package iot.restful;

import iot.IoTPlatform;
import iot.SensorInfo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class SensorsResource {
	@Context private UriInfo m_uriInfo;
	
	@GET
	@Path("/Sensors")
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
	@Path("/Sensors({id})")
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
	@Path("/Sensors({id})/{path:.*}")
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
	@Path("/Sensors/{path:.*}")
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
	{ "Metadata" : "..." }
	 */
	@POST
	@Path("/Sensors")
	public Response create(String info) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		try {
			if ( info == null ) {
				return context.createBadRequest("input Sensors was missing");
			}
			JsonData data = JsonData.create(info);
			
			SensorInfo sensor = create(data);
			Sensor stSensor = new Sensor(sensor, m_uriInfo);
			return Response.created(stSensor.getSelfLink())
							.entity(context.toJsonString(stSensor))
							.build();
		}
		catch ( Exception e ) {
			return context.createExceptionResponse(e);
		}
	}
	
	@PUT
	@Path("/Sensors({id})")
	public Response update(@PathParam("id") long id) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		SensorInfo sensor = context.m_platform.getSensor(id);
		if ( sensor == null ) {
			throw new NotFoundException("invalid Sensor id=" + id);
		}
		
		MultivaluedMap<String, String> params = m_uriInfo.getQueryParameters();
		
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/Sensors({id})")
	public Response delete(@PathParam("id") long id) {
		IoTPlatform platform = SensorThingsContext.getContext().m_platform;
		platform.deleteSensor(id);
		
		return Response.noContent().build();
	}
	
	static SensorInfo create(JsonData sensorInfo) throws BadRequestException {
		String meta = sensorInfo.getString("Metadata").orElse(null);
		if ( meta == null ) {
			throw new BadRequestException("\"Metadata\" is missing");
		}
		
		IoTPlatform platform = SensorThingsContext.getContext().m_platform;
		return platform.createSensor(meta);
	}
	
	static long identify(Optional<JsonData> sensorInfo) {
		if ( !sensorInfo.isPresent() ) {
			throw new BadRequestException("Sensor is not specified");
		}
		Optional<Long> sensorId = sensorInfo.get().getID();
		if ( sensorId.isPresent() ) {
			return sensorId.get();
		}
		
		return create(sensorInfo.get()).getId();
	}
	
	private Stream<Sensor> stream() {
		SensorThingsContext context = SensorThingsContext.getContext();
		return context.m_platform.streamOfSensor().map(s -> new Sensor(s, m_uriInfo));
	}
	
	private Stream<Sensor> stream(long id) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		SensorInfo sensor = context.m_platform.getSensor(id);
		if ( sensor == null ) {
			return Stream.empty();
		}
		
		return Stream.of(new Sensor(sensor, m_uriInfo));
	}
}
