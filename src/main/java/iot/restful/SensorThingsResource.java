package iot.restful;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

import jersey.repackaged.com.google.common.collect.Maps;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class SensorThingsResource {
	private static final String[] COLLECTIONS = {
		"Things", "Locations", "Datastream", "Sensors", "Observations", "FeaturesOfInterest",
		"ObservedProperties", "Subscribers", "TaskingCapabilities", "Actuators", "Tasks",
	};
	
	@Context private UriInfo m_uriInfo;
	
	@GET
	public Response get() {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		try {
			StreamingOutput output = context.executeQuery(Stream.of(new Root(m_uriInfo)), m_uriInfo);
			return Response.ok(output).build();
		}
		catch ( Exception e ) {
			return context.createExceptionResponse(e);
		}
	}
	
	static class Root extends STTuple {
		Root(UriInfo uriInfo) {
			final URI baseUri = uriInfo.getBaseUri();
			List<Map<String,Object>> colls = Stream.of(COLLECTIONS).map(name -> {
				Map<String,String> inner = Maps.newHashMap();
				inner.put("uri", baseUri + name);
				
				Map<String,Object> outer = Maps.newHashMap();
				outer.put(name, inner);
				
				return outer;
			}).collect(Collectors.toList());
			setAttribute("Collections", colls);
		}
	}
}
