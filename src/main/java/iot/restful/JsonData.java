package iot.restful;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import jersey.repackaged.com.google.common.base.Preconditions;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.vividsolutions.jts.geom.Geometry;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class JsonData {
	private final Map<String,Object> m_data;
	
	public static JsonData create(String jsonStr)
		throws JsonParseException, JsonMappingException, IOException {
		return new JsonData(SensorThingsContext.getContext().fromJsonToMap(jsonStr));
	}
	
	public JsonData(Map<String,Object> data) {
		Preconditions.checkNotNull(data);
		
		m_data = data;
	}
	
	public Optional<Long> getID() {
		Integer id = (Integer)m_data.get("ID");
		return (id != null) ? Optional.of(Long.valueOf(id)) : Optional.empty();
	}
	
	public Optional<String> getString(String name) {
		String attr = (String)m_data.get(name);
		return (attr != null) ? Optional.of(attr) : Optional.empty();
	}
	
	public Optional<Integer> getInteger(String name) {
		Integer attr = (Integer)m_data.get(name);
		return (attr != null) ? Optional.of(attr) : Optional.empty();
	}
	
	public Optional<Geometry> getGeometry(String name) throws JsonProcessingException {
		SensorThingsContext context = SensorThingsContext.getContext();

		@SuppressWarnings("unchecked")
		Map<String,Object> attr = (Map<String,Object>)m_data.get(name);
		if ( attr != null ) {
			String geoJsonStr = context.toJsonString(attr);
			Geometry geom = context.fromGeoJson(geoJsonStr);
			return Optional.of(geom);
		}
		else {
			return Optional.empty();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Optional<JsonData> getData(String name) {
		Map<String,Object> attr = (Map<String,Object>)m_data.get(name);
		return (attr != null) ? Optional.of(new JsonData(attr)) : Optional.empty();
	}
	
	@Override
	public String toString() {
		return m_data.toString();
	}
}
