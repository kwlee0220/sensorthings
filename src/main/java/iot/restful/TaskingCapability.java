package iot.restful;

import iot.jdbc.SensorNodeImpl;

import java.util.stream.Stream;

import javax.ws.rs.core.UriInfo;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class TaskingCapability extends STNode {
	private SensorNodeImpl m_node;
	
	public TaskingCapability(SensorNodeImpl node, UriInfo uriInfo) {
		super(node.getId(), "TaskingCapabilities", uriInfo);
		
		m_node = node;
		setAttribute("Description", "TaskingCapability_" + node.getId());
		addLink("Tasks");
	}

	@Override
	public Stream<STTuple> navigate(String link) {
		if ( link.equalsIgnoreCase("Tasks") ) {
			return Stream.of(new Task(m_node, m_uriInfo));
		}
		
		return Stream.empty();
	}
}
