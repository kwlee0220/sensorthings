package iot.restful;

import iot.IoTPlatform;
import iot.SensorNode;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;
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

import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ThingsResource {
	@Context private UriInfo m_uriInfo;
	
	@GET
	@Path("/Things")
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
	@Path("/Things({id})")
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
	@Path("/Things({id})/{path:.*}")
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
	@Path("/Things/{path:.*}")
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
	{"Description" : "Sample Thing" }
	 */
	@POST
	@Path("/Things")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(String info) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		try {
			if ( info == null ) {
				return context.createBadRequest("Thing was not specified");
			}
			SensorNode node = create(JsonData.create(info));
			Thing thing = new Thing(node, m_uriInfo);
			
			return Response.created(thing.getSelfLink())
							.entity(context.toJsonString(thing))
							.build();
		}
		catch ( Exception e ) {
			return context.createExceptionResponse(e);
		}
	}
	
	@DELETE
	@Path("/Things({id})")
	public Response delete(@PathParam("id") long id) {
		IoTPlatform platform = SensorThingsContext.getContext().m_platform;
		platform.deleteSensorNode(id);
		
		return Response.noContent().build();
	}
	
	/*
	{"Description" : "Sample Thing" }
	 */
	@PATCH
	@Path("/Things({id})")
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
	
	static SensorNode create(JsonData foiInfo)
		throws BadRequestException, JsonProcessingException {
		String desc = foiInfo.getString("Description").orElse(null);
		
		IoTPlatform platform = SensorThingsContext.getContext().m_platform;
		return platform.createSensorNode(desc);
	}
	
	static long identify(Optional<JsonData> thingInfo)
		throws BadRequestException, JsonProcessingException {
		if ( !thingInfo.isPresent() ) {
			throw new BadRequestException("Thing is not specified");
		}
		Optional<Long> thingId = thingInfo.get().getID();
		if ( thingId.isPresent() ) {
			return thingId.get();
		}
		
		return create(thingInfo.get()).getId();
	}
	
	private Stream<Thing> stream() {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		return context.m_platform.streamOfSensorNode().map(n -> new Thing(n, m_uriInfo));
	}
	
	private Stream<Thing> stream(long id) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		SensorNode node = context.m_platform.getSensorNode(id);
		if ( node == null ) {
			return Stream.empty();
		}
		
		return Stream.of(new Thing(node, m_uriInfo));
	}
}
