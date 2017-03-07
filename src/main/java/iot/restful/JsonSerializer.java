package iot.restful;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import jersey.repackaged.com.google.common.collect.ImmutableMap;

import org.geotools.geojson.geom.GeometryJSON;

import com.vividsolutions.jts.geom.Geometry;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class JsonSerializer {
	@SuppressWarnings("unchecked")
	public Object serialize(Object obj) {
		if ( obj instanceof Map ) {
			return serialize((Map<String,Object>)obj);
		}
		if ( obj instanceof Instant ) {
			return serialize((Instant)obj);
		}
		if ( obj instanceof Geometry ) {
			return serialize((Geometry)obj);
		}
		if ( obj instanceof JsonSerializable ) {
			return ((JsonSerializable)obj).serialize();
		}
		
		return obj;
	}
	
	public ImmutableMap<String,Object> serialize(Map<String,Object> map) {
		ImmutableMap.Builder<String,Object> builder = new ImmutableMap.Builder<String,Object>();
		
		for ( Map.Entry<String,Object> entry: map.entrySet() ) {
			builder.put(entry.getKey(), serialize(entry.getValue()));
		}
		
		return builder.build();
	}
	
	public Object serialize(Instant ts) {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX")
								.withZone(ZoneOffset.UTC)
								.format(ts);
	}
	
	public Object serialize(Geometry geom) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			GeometryJSON gjson = new GeometryJSON();
			gjson.write(geom, baos);
		}
		catch ( IOException ignored ) { }
		
		return baos.toString();
	}
}
