package iot.restful;

import java.util.Map;

import jersey.repackaged.com.google.common.collect.ImmutableMap;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class STLink implements JsonSerializable {
	private final String m_nodePrefix;
	private final String m_nodeName;
	
	public STLink(String prefix, String name) {
		m_nodePrefix = prefix;
		m_nodeName = name;
	}

	@Override
	public Map<String, Object> serialize() {
		ImmutableMap.Builder<String,Object> builder = new ImmutableMap.Builder<String,Object>();
		
		builder.put("Association-Link", m_nodePrefix + "/$links/" + m_nodeName);
		builder.put("Navigation-Link", m_nodePrefix + "/" + m_nodeName);
		
		return builder.build();
	}

}
