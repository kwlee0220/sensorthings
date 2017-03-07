package iot.restful;

import iot.IoTPlatform;
import iot.SensorNode;
import iot.SensorValueStream;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.ws.rs.Consumes;
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


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DatastreamsResource {
	@Context private UriInfo m_uriInfo;
	
	@GET
	@Path("/Datastreams")
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
	@Path("/Datastreams({id})")
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
	@Path("/Datastreams({id})/{path:.*}")
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
	@Path("/Datastreams/{path:.*}")
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
	
	@POST
	@Path("/Datastreams")
	public Response create(String info) {
		SensorThingsContext context = SensorThingsContext.getContext();
		IoTPlatform platform = context.m_platform;
		
		try {
			if ( info == null ) {
				return context.createBadRequest("input Datastreams was missing");
			}
			JsonData data = JsonData.create(info);

			long thingId = ThingsResource.identify(data.getData("Thing"));
			long propId = ObservedPropertiesResource.identify(data.getData("ObservedProperty"));
			String desc = data.getString("Description").orElse(null);
			
			SensorValueStream vstream = platform.createSensorValueStream(thingId, propId, desc);
			Datastream stream = new Datastream(vstream, m_uriInfo);
			
			return Response.created(stream.getSelfLink())
							.entity(context.toJsonString(stream))
							.build();
		}
		catch ( Exception e ) {
			return context.createExceptionResponse(e);
		}
	}
	
	@DELETE
	@Path("/Datastreams({id})")
	public Response delete(@PathParam("id") long id) {
		IoTPlatform platform = SensorThingsContext.getContext().m_platform;
		platform.deleteSensorValueStream(id);
		
		return Response.noContent().build();
	}
	
	/*
	{"Description" : "Sample Thing" }
	 */
	@PATCH
	@Path("/Datastreams({id})")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") long id, String info) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		try {
			if ( info == null ) {
				return Response.noContent().build();
			}
			
			SensorNode node = context.m_platform.getSensorNode(id);
			if ( node == null ) {
				throw new NotFoundException("invalid SensorNode id=" + id);
			}

			JsonData data = JsonData.create(info);
			Map<String,Object> udata = Maps.newLinkedHashMap();
			
			String desc = data.getString("Description").orElse(null);
			if ( desc != null ) {
				udata.put("desc", desc);
			}
			context.m_platform.updateSensorNode(id, udata);
			
			return Response.noContent().build();
		}
		catch ( Exception e ) {
			return context.createExceptionResponse(e);
		}
	}
	
	private Stream<Datastream> stream() {
		SensorThingsContext context = SensorThingsContext.getContext();
		return context.m_platform.streamOfSensorValueStream()
								.map(s -> new Datastream(s, m_uriInfo));
	}
	
	private Stream<Datastream> stream(long id) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		SensorValueStream stream = context.m_platform.getSensorValueStream(id);
		if ( stream == null ) {
			return Stream.empty();
		}
		
		return Stream.of(new Datastream(stream, m_uriInfo));
	}
}
