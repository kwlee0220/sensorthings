package iot.restful;

import iot.jdbc.SensorNodeImpl;

import java.util.stream.Stream;

import javax.ws.rs.core.UriInfo;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class Task extends STNode {
	private SensorNodeImpl m_node;
	
	public Task(SensorNodeImpl node, UriInfo uriInfo) {
		super(node.getId(), "Task", uriInfo);
		
		m_node = node;
		setAttribute("Metadata", "task_" + node.getId());
		addLink("TaskingCapability");
	}

	@Override
	public Stream<STTuple> navigate(String link) {
		if ( link.equalsIgnoreCase("TaskingCapability") ) {
			return Stream.of(new TaskingCapability(m_node, m_uriInfo));
		}
		
		return Stream.empty();
	}
}
