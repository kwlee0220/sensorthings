package iot.restful;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import jersey.repackaged.com.google.common.collect.Maps;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class STTuple implements JsonSerializable {
	protected final Map<String,Object> m_fields = Maps.newHashMap();
	
	public STTuple() { }
	
	public STTuple(String key, Object data) {
		m_fields.put(key, data);
	}
	
	public STTuple(Map<String,Object> fields) {
		m_fields.putAll(fields);
	}
	
	public Object getAttribute(String key) {
		return  m_fields.get(key);
	}
	
	public STTuple setAttribute(String key, Object value) {
		m_fields.put(key, value);
		
		return this;
	}
	
	public Set<String> getAttributeNameAll() {
		return m_fields.keySet();
	}
	
	public Collection<Object> getAttributeAll() {
		return m_fields.values();
	}
	
	public Map<String,Object> asMap() {
		return Collections.unmodifiableMap(m_fields);
	}
	
	public STTuple duplicate() {
		return new STTuple(m_fields);
	}
	
	public <T extends STTuple> T project(T result, Collection<String> attrNames) {
		for ( String attrName: attrNames ) {
			Object attr = m_fields.get(attrName);
			if ( attr != null ) {
				result.setAttribute(attrName, attr);
			}
		}
		
		return result;
	}
	
	public Stream<STTuple> navigate(List<String> path) {
		Stream<STTuple> stream = Stream.of(this);
		for ( String attrName: path ) {
			stream = stream.flatMap(t -> t.navigate(attrName));
		}
		
		return stream;
	}
	
	public Stream<STTuple> navigate(String link) {
		Object field = getAttribute(link);
		if ( field != null ) {
			return Stream.of((new STTuple(link, field)));
		}
		
		return Stream.empty();
	}

	@Override
	public Map<String, Object> serialize() {
		return new JsonSerializer().serialize(m_fields);
	}
	
	@Override
	public String toString() {
		return m_fields.toString();
	}
}
