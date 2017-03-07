package iot.restful;

import iot.IoTPlatform;
import iot.Measurement;
import iot.SensorNode;

import java.util.Collections;
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
public class ObservedPropertiesResource {
	@Context private UriInfo m_uriInfo;
	
	@GET
	@Path("/ObservedProperties")
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
	@Path("/ObservedProperties({id})")
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
	@Path("/ObservedProperties({id})/{path:.*}")
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
	@Path("/ObservedProperties/{path:.*}")
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
		"UnitOfMeasurement": "Kg",
		"URI": "urn:weight"
	}
	 */
	@POST
	@Path("/ObservedProperties")
	public Response create(String info) {
		SensorThingsContext context = SensorThingsContext.getContext();

		try {
			if ( info == null ) {
				return context.createBadRequest("input ObservedProperty was missing");
			}
			
			Measurement m = create(JsonData.create(info));
			ObservedProperty prop = new ObservedProperty(m, m_uriInfo);
			
			return Response.created(prop.getSelfLink())
							.entity(context.toJsonString(prop))
							.build();
		}
		catch ( Exception e ) {
			return context.createExceptionResponse(e);
		}
	}
	
	@DELETE
	@Path("/ObservedProperties({id})")
	public Response delete(@PathParam("id") long id) {
		IoTPlatform platform = SensorThingsContext.getContext().m_platform;
		platform.deleteMeasurement(id);
		
		return Response.noContent().build();
	}
	
	/*
	{
		"UnitOfMeasurement": "Kg",
		"URI": "urn:weight"
	}
	 */
	@PATCH
	@Path("/ObservedProperties({id})")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") long id, String info) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		try {
			if ( info == null ) {
				return Response.noContent().build();
			}
			
			Measurement measurement = context.m_platform.getMeasurement(id);
			if ( measurement == null ) {
				throw new NotFoundException("invalid ObservedProperties id=" + id);
			}

			JsonData data = JsonData.create(info);
			Map<String,Object> udata = Maps.newLinkedHashMap();
			
			String unit = data.getString("UnitOfMeasurement").orElse(null);
			if ( unit != null ) {
				udata.put("unit", unit);
			}
			
			String urn = data.getString("URI").orElse(null);
			if ( urn != null ) {
				udata.put("urn", urn);
			}
			context.m_platform.updateMeasurement(id, udata);
			
			return Response.noContent().build();
		}
		catch ( Exception e ) {
			return context.createExceptionResponse(e);
		}
	}
	
	static Measurement create(JsonData data) throws BadRequestException {
		String unit = data.getString("UnitOfMeasurement").orElse(null);
		if ( unit == null ) {
			throw new BadRequestException("missing attribute=\"UnitOfMeasurement\"");
		}
		
		String urn = data.getString("URI").orElse(null);
		if ( urn == null ) {
			throw new BadRequestException("missing attribute=\"URI\"");
		}

		IoTPlatform platform = SensorThingsContext.getContext().m_platform;
		return platform.createMeasurement(urn, unit);
	}
	
	static long identify(Optional<JsonData> propData)
		throws BadRequestException, JsonProcessingException {
		if ( !propData.isPresent() ) {
			throw new BadRequestException("ObservedProperty is not specified");
		}
		Optional<Long> oid = propData.get().getID();
		if ( oid.isPresent() ) {
			return oid.get();
		}
		
		return create(propData.get()).getId();
	}
	
	private Stream<ObservedProperty> stream() {
		SensorThingsContext context = SensorThingsContext.getContext();
		return context.m_platform.streamOfMeasurement()
						.map(m -> new ObservedProperty(m, m_uriInfo));
	}
	
	private Stream<ObservedProperty> stream(long id) {
		SensorThingsContext context = SensorThingsContext.getContext();
		
		Measurement m = context.m_platform.getMeasurement(id);
		if ( m == null ) {
			return Stream.empty();
		}
		
		return Stream.of(new ObservedProperty(m, m_uriInfo));
	}
}
