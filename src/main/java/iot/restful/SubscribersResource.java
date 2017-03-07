package iot.restful;

import iot.IoTPlatform;
import iot.SensorValueSubscriber;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
public class SubscribersResource {
	@Context private UriInfo m_uriInfo;
	
	@GET
	@Path("/Subscribers")
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
	@Path("/Subscribers({id})")
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
	@Path("/Subscribers({id})/{path:.*}")
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
	@Path("/Subscribers/{path:.*}")
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
		"Channel" : "DE:AD:BE:EF:FE:A0/a0",
		"Datastream" : { "ID" : 8 },
		"Sensor" : { "Metadata" : "test" },
		"FeatureOfInterest" : { 
			"Geometry": {
				"type" : "Point",
				"coordinates": [100, 200]
			}
		}
	}
	 */
	@POST
	@Path("/Subscribers")
	public Response create(String info) {
		SensorThingsContext context = SensorThingsContext.getContext();
		IoTPlatform platform = context.m_platform;
		
		try {
			if ( info == null ) {
				return context.createBadRequest("input Datastreams was missing");
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
			
			long sensorId = SensorsResource.identify(data.getData("Sensor"));
			long foiId = FeatureOfInterestResource.identify(data.getData("FeatureOfInterest"));
			
			String channel = data.getString("Channel").orElse(null);
			if ( channel == null ) {
				return context.createBadRequest("Channel is not specified");
			}
			
			SensorValueSubscriber subscriber = platform.createSensorValueSubscriber(channel,
																streamId.get(), sensorId, foiId);
			Subscriber stream = new Subscriber(subscriber, m_uriInfo);
			
			return Response.created(stream.getSelfLink())
							.entity(context.toJsonString(stream))
							.build();
		}
		catch ( Exception e ) {
			return context.createExceptionResponse(e);
		}
	}
	
	@DELETE
	@Path("/Subscribers({id})")
	public Response delete(@PathParam("id") long id) {
		IoTPlatform platform = SensorThingsContext.getContext().m_platform;
		platform.deleteSensorValueSubscriber(id);
		
		return Response.noContent().build();
	}
	
	/*
	{"Channel" : "Sample Thing" }
	 */
	@PATCH
	@Path("/Subscribers({id})")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") long id, String info) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		try {
			if ( info == null ) {
				return Response.noContent().build();
			}
			
			SensorValueSubscriber subscriber = context.m_platform.getSensorValueSubscriber(id);
			if ( subscriber == null ) {
				throw new NotFoundException("invalid Subscriber id=" + id);
			}

			JsonData data = JsonData.create(info);
			Map<String,Object> udata = Maps.newLinkedHashMap();
			
			String channel = data.getString("Channel").orElse(null);
			if ( channel != null ) {
				udata.put("channel", channel);
			}
			context.m_platform.updateSensorValueSubscriber(id, udata);
			
			return Response.noContent().build();
		}
		catch ( Exception e ) {
			return context.createExceptionResponse(e);
		}
	}
	
	private Stream<Subscriber> stream() {
		SensorThingsContext context = SensorThingsContext.getContext();
		return context.m_platform.streamOfSensorValueSubscriber()
								.map(s -> new Subscriber(s, m_uriInfo));
	}
	
	private Stream<Subscriber> stream(long id) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		SensorValueSubscriber subscriber = context.m_platform.getSensorValueSubscriber(id);
		if ( subscriber == null ) {
			return Stream.empty();
		}
		
		return Stream.of(new Subscriber(subscriber, m_uriInfo));
	}
}
