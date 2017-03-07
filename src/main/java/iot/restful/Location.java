package iot.restful;

import iot.SensorNodeLocation;

import java.util.stream.Stream;

import javax.ws.rs.core.UriInfo;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class Location extends STNode {
	private SensorNodeLocation m_loc;
	
	public Location(SensorNodeLocation loc, UriInfo uriInfo) {
		super(loc.getId(), "Locations", uriInfo);
		
		m_loc = loc;
		setAttribute("Time", loc.getTimestamp());
		setAttribute("Geometry", new STGeometry(loc.getGeometry()));
		addLink("Things");
	}

	@Override
	public Stream<STTuple> navigate(String link) {
		if ( link.equalsIgnoreCase("Things") ) {
			return m_loc.getSensorNodeAll().map(node -> new Thing(node, m_uriInfo));
		}
		
		return super.navigate(link);
	}
}
