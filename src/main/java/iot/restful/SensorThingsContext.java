package iot.restful;

import iot.IoTPlatformException;
import iot.jdbc.IoTPlatformImpl;
import iot.restful.parser.FilterExprLexer;
import iot.restful.parser.FilterExprParser;
import iot.restful.parser.FilterExprParser.RelExprContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

import jersey.repackaged.com.google.common.base.Splitter;
import jersey.repackaged.com.google.common.collect.Iterables;
import jersey.repackaged.com.google.common.collect.Lists;
import jersey.repackaged.com.google.common.collect.Maps;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.geotools.geojson.geom.GeometryJSON;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
class SensorThingsContext {
	static SensorThingsContext s_context;
	
	IoTPlatformImpl m_platform;
	private final ObjectMapper m_mapper;
	final TypeReference<HashMap<String,Object>> m_typeRef
											= new TypeReference<HashMap<String,Object>>() { };
	final GeometryFactory s_geomFactory = new GeometryFactory();
	final Splitter s_csvSplitter = Splitter.on(',').trimResults();
	
	SensorThingsContext(IoTPlatformImpl platform) {
		m_platform = platform;
		
		m_mapper = new ObjectMapper();
		m_mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		m_mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		m_mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
	}
	
	static SensorThingsContext getContext() {
		return s_context;
	}
	
	static void setContext(SensorThingsContext context) {
		s_context = context;
	}
	
	Stream<STTuple> afterProcess(Stream<? extends STTuple> start, List<PathSegment> segs,
								UriInfo uriInfo) {
		List<String> path = Lists.newArrayList(Iterables.transform(segs, seg -> seg.getPath()));
		Stream<STTuple> tupleStream = start.flatMap(n -> n.navigate(path));
		
		String vstr;
		vstr = getParameterValue(uriInfo, "$filter");
		if ( vstr != null ) {
			try {
				ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(vstr.getBytes()));
				FilterExprLexer lexer = new FilterExprLexer(input);
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				FilterExprParser parser = new FilterExprParser(tokens, s_geomFactory);
				parser.setBuildParseTree(false);
				
				RelExprContext result = parser.relExpr();
				tupleStream = tupleStream.filter(result.evaltor::eval);
			}
			catch ( IOException ignored ) { }
		}

		vstr = getParameterValue(uriInfo, "$count");
		if ( vstr != null ) {
			if ( Boolean.parseBoolean(vstr) ) {
				List<STTuple> tuples = tupleStream.collect(Collectors.toList());
				int count = tuples.size();
				tupleStream = Stream.concat(Stream.of(new STTuple("count", count)), tuples.stream());
			}
		}

		vstr = getParameterValue(uriInfo, "$skip");
		if ( vstr != null ) {
			int skip = Integer.parseInt(vstr);
			tupleStream = tupleStream.skip(skip);
		}

		vstr = getParameterValue(uriInfo, "$top");
		if ( vstr != null ) {
			int top = Integer.parseInt(vstr);
			tupleStream = tupleStream.limit(top);
		}

		vstr = getParameterValue(uriInfo, "$distinct");
		if ( vstr != null ) {
			List<String> fields = Lists.newArrayList(s_csvSplitter.split(vstr));
			tupleStream = tupleStream.sorted(new TupleComparator(fields));
			tupleStream = tupleStream.filter(new DistinctPredicate(fields));
		}

		vstr = getParameterValue(uriInfo, "$orderby");
		if ( vstr != null ) {
			List<String> fields = Lists.newArrayList(s_csvSplitter.split(vstr));
			tupleStream = tupleStream.sorted(new TupleComparator(fields));
		}
		
		vstr = getParameterValue(uriInfo, "$expand");
		if ( vstr != null ) {
			List<String> fields = Lists.newArrayList(s_csvSplitter.split(vstr));
			for ( String field: fields ) {	
				tupleStream = tupleStream.map(new Expander(field));
			}
		}

		vstr = getParameterValue(uriInfo, "$select");
		if ( vstr != null ) {
			List<String> fields = Lists.newArrayList(s_csvSplitter.split(vstr));
			tupleStream = tupleStream.map(n -> n.project(new STTuple(), fields));
		}
		
		return tupleStream;
	}

	private static final List<PathSegment> EMPTY_PATH = Collections.<PathSegment>emptyList();
	StreamingOutput executeQuery(Stream<? extends STTuple> start, UriInfo uriInfo) {
		return executeQuery(start, EMPTY_PATH, uriInfo);
	}
	
	StreamingOutput executeQuery(Stream<? extends STTuple> start, List<PathSegment> segs,
											UriInfo uriInfo) {
		Stream<STTuple> tstream = afterProcess(start, segs, uriInfo);
		return new STNodeStreamingOutput(tstream, m_mapper);
	}
	
	static class STNodeStreamingOutput implements StreamingOutput {
		private final ObjectMapper m_mapper;
		private final Stream<STTuple> m_tstream;
		
		STNodeStreamingOutput(Stream<STTuple> tstream, ObjectMapper mapper) {
			m_tstream = tstream;
			m_mapper = mapper;
		}

		@Override
		public void write(OutputStream os) throws IOException, WebApplicationException {
			final JsonGenerator gen = m_mapper.getFactory().createGenerator(os);
			try {
				gen.writeStartArray();
				m_tstream.map(t -> t.serialize()).forEach(data -> {
					try {
						gen.writeObject(data);
					}
					catch ( Exception ignored ) { }
				});
				gen.writeEndArray();
			}
			finally {
				gen.close();
			}
		}
	}
	
	String toJsonString(Map<String,Object> data) throws JsonProcessingException {
		return m_mapper.writeValueAsString(data);
	}
	
	String toJsonString(STNode node) {
		try {
			return m_mapper.writeValueAsString(node.serialize());
		}
		catch ( JsonProcessingException e ) {
			return null;
		}
	}
	
	Map<String,Object> fromJsonToMap(String jsonStr)
		throws JsonParseException, JsonMappingException, IOException {
		return m_mapper.readValue(jsonStr, m_typeRef);
	}
	
	Map<String,Object> toGeoJsonMap(Geometry geom) {
		try {
			return fromJsonToMap(toGeoJson(geom));
		}
		catch ( Exception e ) {
			throw new RuntimeException();
		}
	}
	
	String toGeoJson(Geometry geom) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			GeometryJSON gjson = new GeometryJSON();
			gjson.write(geom, baos);
		}
		catch ( IOException ignored ) { }
		
		return baos.toString();
	}
	
	Geometry fromGeoJson(String jsonStr) {
		ByteArrayInputStream bais = new ByteArrayInputStream(jsonStr.getBytes());
		try {
			GeometryJSON gjson = new GeometryJSON();
			return gjson.read(bais);
		}
		catch ( IOException ignored ) {
			throw new RuntimeException(ignored);
		}
		finally {
			try {
				bais.close();
			}
			catch ( IOException ignored ) { }
		}
	}
	
	public Response createBadRequest(String msg) {
		Map<String,Object> data = Maps.newLinkedHashMap();
		data.put("Message", msg);
		
		try {
			return Response.status(Status.BAD_REQUEST)
							.entity(s_context.toJsonString(data))
							.build();
		}
		catch ( JsonProcessingException neverHappens ) {
			throw new RuntimeException(neverHappens);
		}
	}
	
	public Response createExceptionResponse(Throwable e) {
		if ( e instanceof IoTPlatformException ) {
			Throwable cause = ((IoTPlatformException)e).getCause();
			if ( cause != null ) {
				e = cause;
			}
		}
		if ( e instanceof BadRequestException ) {
			return createBadRequest(((BadRequestException)e).getMessage());
		}
		
		try {
			Map<String,Object> data = Maps.newLinkedHashMap();
			data.put("Error", e.getClass().getName());
			if ( e.getMessage() != null ) {
				data.put("Message", e.getMessage());
			}
			String jsonStr = s_context.toJsonString(data);

			return Response.serverError().entity(jsonStr).build();
		}
		catch ( JsonProcessingException neverHappens ) {
			throw new RuntimeException(neverHappens);
		}
	}
	
	private String getParameterValue(UriInfo uriInfo, String key) {
		List<String> values = uriInfo.getQueryParameters().get(key);
		if ( values == null ) {
			return null;
		}
		
		return Iterables.getFirst(values, null);
	}
	
	static class TupleComparator implements Comparator<STTuple> {
		private final List<String> m_fields;
		
		TupleComparator(List<String> fields) {
			m_fields = fields;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public int compare(STTuple t1, STTuple t2) {
			for ( String name: m_fields ) {
				Object attr1 = t1.getAttribute(name);
				Object attr2 = t2.getAttribute(name);
				
				int result = 0;
				if ( attr1 != null && attr2 != null ) {
					if ( attr1 instanceof Comparable ) {
						result = ((Comparable)attr1).compareTo(attr2);
					}
				}
				else if ( attr1 != null ) {
					result = 1;
				}
				else if ( attr2 != null ) {
					result = -1;
				}
				
				if ( result != 0 ) {
					return result;
				}
			}
			
			return 0;
		}
	}
	
	static class DistinctPredicate implements Predicate<STTuple> {
		private STTuple m_last;
		private TupleComparator m_cmp;
		
		DistinctPredicate(List<String> fields) {
			m_cmp = new TupleComparator(fields);
		}

		@Override
		public boolean test(STTuple tuple) {
			int cmp = (m_last != null) ? m_cmp.compare(m_last, tuple) : 1;
			m_last = tuple;
			
			return cmp != 0;
		}
	}
}
