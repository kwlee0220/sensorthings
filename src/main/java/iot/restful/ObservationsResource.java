package iot.restful;

import iot.IoTPlatform;
import iot.SensorValueLog;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ObservationsResource {
	@Context private UriInfo m_uriInfo;
	
	@GET
	@Path("/Observations")
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
	@Path("/Observations({id})")
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
	@Path("/Observations({id})/{path:.*}")
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
	@Path("/Observations/{path:.*}")
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
		"Datastream" : { "ID" : 9 },
		"Sensor" : { "ID" : 41 },
		"FeatureOfInterest" : { "ID" : 7 },
		"ResultType" : "Calculated",
		"ResultValue" : 234
	}
	 */
	@POST
	@Path("/Observations")
	public Response create(String info) {
		SensorThingsContext context = SensorThingsContext.getContext();
		IoTPlatform platform = context.m_platform;
		
		try {
			if ( info == null ) {
				return context.createBadRequest("input Observation was missing");
			}
			JsonData data = JsonData.create(info);
			
			Optional<JsonData> streamInfo = data.getData("Datastream");
			if ( !streamInfo.isPresent() ) {
				return context.createBadRequest("Datastream is not specified");
			}
			Optional<Long> streamId = streamInfo.get().getID();
			if ( !streamId.isPresent() ) {
				return context.createBadRequest("Datastream/ID is not specified");
			}
			
			Optional<JsonData> sensorInfo = data.getData("Sensor");
			if ( !sensorInfo.isPresent() ) {
				return context.createBadRequest("Sensor is not specified");
			}
			Optional<Long> sensorId = sensorInfo.get().getID();
			if ( !sensorId.isPresent() ) {
				return context.createBadRequest("Sensor/ID is not specified");
			}
			
			Optional<JsonData> foiInfo = data.getData("FeatureOfInterest");
			if ( !foiInfo.isPresent() ) {
				return context.createBadRequest("FeatureOfInterest is not specified");
			}
			Optional<Long> foiId = foiInfo.get().getID();
			if ( !foiId.isPresent() ) {
				return context.createBadRequest("FeatureOfInterest/ID is not specified");
			}
			
			Optional<Integer> ovalue = data.getInteger("ResultValue");
			if ( !ovalue.isPresent() ) {
				return context.createBadRequest("Observation has an invalid \"ResultValue\" field");
			}
			
			String valueType = data.getString("ResultType").orElse(null);
			
			long logId = platform.createSensorValueLog(sensorId.get(), streamId.get(), foiId.get(),
														valueType, ovalue.get());
			SensorValueLog log = platform.getSensorValueLog(logId);
			Observation ob = new Observation(log, m_uriInfo);
			
			return Response.created(ob.getSelfLink())
							.entity(context.toJsonString(ob))
							.build();
		}
		catch ( Exception e ) {
			return context.createExceptionResponse(e);
		}
	}
	
	@DELETE
	@Path("/Observations({id})")
	public Response delete(@PathParam("id") long id) {
		IoTPlatform platform = SensorThingsContext.getContext().m_platform;
		platform.deleteSensor(id);
		
		return Response.noContent().build();
	}
	
	private Stream<Observation> stream() {
		SensorThingsContext context = SensorThingsContext.getContext();
		return context.m_platform.streamOfSensorValueLog().map(s -> new Observation(s, m_uriInfo));
	}
	
	private Stream<Observation> stream(long id) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		SensorValueLog log = context.m_platform.getSensorValueLog(id);
		if ( log == null ) {
			return Stream.empty();
		}
		
		return Stream.of(new Observation(log, m_uriInfo));
	}
}
