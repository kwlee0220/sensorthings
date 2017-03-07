package iot.restful;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.UriInfo;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public abstract class STNode extends STTuple {
	protected UriInfo m_uriInfo;
	private final long m_id;
	private final String m_nodePrefix;
	
	protected STNode(long id, String nodeName, UriInfo info) {
		m_id = id;
		m_uriInfo = info;
		
		m_nodePrefix = nodeName + "(" + id + ")";
		setAttribute("ID", id);
		setAttribute("Self-Link", info.getBaseUri() + m_nodePrefix);
	}
	
	public long getId() {
		return m_id;
	}
	
	public URI getSelfLink() {
		try {
			return new URI((String)getAttribute("Self-Link"));
		}
		catch ( URISyntaxException neverHappens ) {
			throw new RuntimeException(neverHappens);
		}
	}
	
	protected void addLink(String name) {
		setAttribute(name, new STLink(m_nodePrefix, name));
	}
}
