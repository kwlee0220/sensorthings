package iot.restful;

import iot.SensorValueStream;

import java.util.stream.Stream;

import javax.ws.rs.core.UriInfo;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class Datastream extends STNode {
	private final SensorValueStream m_stream;
	
	public Datastream(SensorValueStream stream, UriInfo uriInfo) {
		super(stream.getId(), "Datastreams", uriInfo);
		
		m_stream = stream;
		setAttribute("Description", m_stream.getDescription());
		addLink("Thing");
		addLink("ObservedProperty");
		addLink("Observations");
	}

	@Override
	public Stream<STTuple> navigate(String link) {
		if ( link.equalsIgnoreCase("Thing") ) {
			return Stream.of(new Thing(m_stream.getSensorNode(), m_uriInfo));
		}
		if ( link.equalsIgnoreCase("ObservedProperty") ) {
			return Stream.of(new ObservedProperty(m_stream.getMeasurement(), m_uriInfo));
		}
		if ( link.equalsIgnoreCase("Observations") ) {
			return m_stream.streamOfValue()
							.map(v -> new Observation(v, m_uriInfo));
		}
		
		return super.navigate(link);
	}
}
