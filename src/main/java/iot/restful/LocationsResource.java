package iot.restful;

import iot.IoTPlatform;
import iot.SensorNodeLocation;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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

import com.vividsolutions.jts.geom.Geometry;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class LocationsResource {
	@Context private UriInfo m_uriInfo;
	
	@GET
	@Path("/Locations")
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
	@Path("/Locations({id})")
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
	@Path("/Locations({id})/{path:.*}")
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
	@Path("/Locations/{path:.*}")
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
		"Thing" : { "ID": 3 },
		"Geometry": {
			"type" : "Point",
			"coordinates": [100, 200]
		}
	}
	 */
	@POST
	@Path("/Locations")
	public Response create(String jsonStr) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		try {
			if ( jsonStr == null ) {
				return context.createBadRequest("input Location was missing");
			}
			JsonData data = JsonData.create(jsonStr);
			
			long thingId = ThingsResource.identify(data.getData("Thing"));
			
			Geometry geom = data.getGeometry("Geometry").orElse(null);
			if ( geom == null ) {
				return context.createBadRequest("Geometry is not specified");
			}

			SensorNodeLocation log = context.m_platform.createSensorNodeLocation(thingId, geom);
			Location loc = new Location(log, m_uriInfo);
			
			return Response.created(loc.getSelfLink())
							.entity(context.toJsonString(loc))
							.build();
		}
		catch ( Exception e ) {
			return context.createExceptionResponse(e);
		}
	}
	
	/*
	{
		"Geometry": { "type" : "Point", "coordinates": [100, 200] },
		"Time" : "..."
	}
	 */
	@PATCH
	@Path("/Locations({id})")
	public Response update(@PathParam("id") long id, String info) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		try {
			if ( info == null ) {
				return Response.noContent().build();
			}
			
			SensorNodeLocation loc = context.m_platform.getSensorNodeLocation(id);
			if ( loc == null ) {
				throw new NotFoundException("invalid Location id=" + id);
			}

			JsonData data = JsonData.create(info);
			Map<String,Object> udata = Maps.newLinkedHashMap();
			
			String timeStr = data.getString("Time").orElse(null);
			if ( timeStr != null ) {
				udata.put("ts", Instant.parse(timeStr));
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
	
	@DELETE
	@Path("/Locations({id})")
	public Response delete(@PathParam("id") long id) {
		IoTPlatform platform = SensorThingsContext.getContext().m_platform;
		platform.deleteSensorNodeLocation(id);
		
		return Response.noContent().build();
	}
	
	private Stream<Location> stream() {
		SensorThingsContext context = SensorThingsContext.getContext();
		return context.m_platform.streamOfSensorNodeLocation().map(n -> new Location(n, m_uriInfo));
	}
	
	private Stream<Location> stream(long id) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		SensorNodeLocation snLoc = context.m_platform.getSensorNodeLocation(id);
		if ( snLoc == null ) {
			return Stream.empty();
		}
		
		return Stream.of(new Location(snLoc, m_uriInfo));
	}
}
