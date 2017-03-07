package iot.restful;

import iot.SensorNode;

import java.util.stream.Stream;

import javax.ws.rs.core.UriInfo;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class Thing extends STNode {
	private final SensorNode m_node;
	
	public Thing(SensorNode node, UriInfo uriInfo) {
		super(node.getId(), "Things", uriInfo);
		
		m_node = node;
		setAttribute("Description", node.getDescription());
		addLink("Locations");
		addLink("Datastreams");
		addLink("TaskingCapabilities");
	}

	@Override
	public Stream<STTuple> navigate(String link) {
		if ( link.equalsIgnoreCase("Locations") ) {
			return m_node.streamOfLocation().map(loc -> new Location(loc, m_uriInfo));
		}
		if ( link.equalsIgnoreCase("Datastreams") ) {
			return m_node.streamOfSensorValueStreamAll()
							.map(s -> new Datastream(s, m_uriInfo));
		}
		
		return super.navigate(link);
	}
	
	@Override
	public Thing duplicate() {
		Thing thing = new Thing(m_node, m_uriInfo);
		thing.m_fields.putAll(m_fields);
		
		return thing;
	}
}
