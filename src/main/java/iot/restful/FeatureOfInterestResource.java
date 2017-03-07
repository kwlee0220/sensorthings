package iot.restful;

import iot.IoTPlatform;
import iot.SensingTarget;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

import jersey.repackaged.com.google.common.collect.Maps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vividsolutions.jts.geom.Geometry;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class FeatureOfInterestResource {
	@Context private UriInfo m_uriInfo;
	
	@GET
	@Path("/FeatureOfInterest")
	public Response getAll() {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		try {
			StreamingOutput output = context.executeQuery(stream(), m_uriInfo);
			return Response.ok(output).build();
		}
		catch ( Exception e ) {
			return context.createExceptionResponse(e);
		}
	}
	
	@GET
	@Path("/FeatureOfInterest({id})")
	public Response get(@PathParam("id") long id) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		try {
			StreamingOutput output = context.executeQuery(stream(id), m_uriInfo);
			return Response.ok(output).build();
		}
		catch ( Exception e ) {
			return context.createExceptionResponse(e);
		}
	}
	
	@GET
	@Path("/FeatureOfInterest({id})/{path:.*}")
	public Response traverse(@PathParam("id") long id, @PathParam("path") List<PathSegment> path) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		try {
			StreamingOutput output = context.executeQuery(stream(id), path, m_uriInfo);
			return Response.ok(output).build();
		}
		catch ( Exception e ) {
			return context.createExceptionResponse(e);
		}
	}
	
	@GET
	@Path("/FeatureOfInterest/{path:.*}")
	public Response traverse(@PathParam("path") List<PathSegment> path) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		try {
			StreamingOutput output = context.executeQuery(stream(), path, m_uriInfo);
			return Response.ok(output).build();
		}
		catch ( Exception e ) {
			return context.createExceptionResponse(e);
		}
	}
	
	/*
	{
		"Geometry": {
			"type" : "Point",
			"coordinates": [ 100, 200 ]
		},
		"Description": "FOI_7"
	}
	 */
	@POST
	@Path("/FeatureOfInterest")
	public Response create(String info) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		try {
			if ( info == null ) {
				return context.createBadRequest("input FeatureOfInterest was missing");
			}
			
			SensingTarget target = create(JsonData.create(info));
			FeatureOfInterest foi = new FeatureOfInterest(target, m_uriInfo);
			
			return Response.created(foi.getSelfLink())
							.entity(context.toJsonString(foi))
							.build();
		}
		catch ( Exception e ) {
			return context.createExceptionResponse(e);
		}
	}
	
	@DELETE
	@Path("/FeatureOfInterest({id})")
	public Response delete(@PathParam("id") long id) {
		IoTPlatform platform = SensorThingsContext.getContext().m_platform;
		platform.deleteSensingTarget(id);
		
		return Response.noContent().build();
	}
	
	/*
	{
		"Geometry": { "type" : "Point", "coordinates": [100, 200] },
		"Description": "FOI_7"
	}
	 */
	@PATCH
	@Path("/FeatureOfInterest({id})")
	public Response update(@PathParam("id") long id, String info) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		try {
			if ( info == null ) {
				return Response.noContent().build();
			}
			
			SensingTarget target = context.m_platform.getSensingTarget(id);
			if ( target == null ) {
				throw new NotFoundException("invalid FeatureOfInterest id=" + id);
			}

			JsonData data = JsonData.create(info);
			Map<String,Object> udata = Maps.newLinkedHashMap();
			
			String desc = data.getString("Description").orElse(null);
			if ( desc != null ) {
				udata.put("desc", Instant.parse(desc));
			}
			Geometry geom = data.getGeometry("Geometry").orElse(null);
			if ( geom != null ) {
				udata.put("geom", geom);
			}
			context.m_platform.updateSensorNodeLocation(id, udata);
			
			return Response.noContent().build();
		}
		catch ( Exception e ) {
			return context.createExceptionResponse(e);
		}
	}
	
	static SensingTarget create(JsonData foiInfo)
		throws BadRequestException, JsonProcessingException {
		Geometry geom = foiInfo.getGeometry("Geometry").orElse(null);
		if ( geom == null ) {
			throw new BadRequestException("\"Geometry\" is missing");
		}
		
		String desc = foiInfo.getString("Description").orElse(null);
		
		IoTPlatform platform = SensorThingsContext.getContext().m_platform;
		return platform.createSensingTarget(geom, desc);
	}
	
	static long identify(Optional<JsonData> foiInfo)
		throws BadRequestException, JsonProcessingException {
		if ( !foiInfo.isPresent() ) {
			throw new BadRequestException("FeatureOfInterest is not specified");
		}
		Optional<Long> foiId = foiInfo.get().getID();
		if ( foiId.isPresent() ) {
			return foiId.get();
		}
		
		return create(foiInfo.get()).getId();
	}
	
	private Stream<FeatureOfInterest> stream() {
		SensorThingsContext context = SensorThingsContext.getContext();
		return context.m_platform.streamOfSensingTarget()
								.map(t -> new FeatureOfInterest(t, m_uriInfo));
	}
	
	private Stream<FeatureOfInterest> stream(long id) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		SensingTarget m = context.m_platform.getSensingTarget(id);
		if ( m == null ) {
			return Stream.empty();
		}
		
		return Stream.of(new FeatureOfInterest(m, m_uriInfo));
	}
}
