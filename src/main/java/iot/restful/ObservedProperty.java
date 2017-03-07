package iot.restful;

import iot.Measurement;

import java.util.stream.Stream;

import javax.ws.rs.core.UriInfo;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class ObservedProperty extends STNode {
	private final Measurement m_measurement;
	
	public ObservedProperty(Measurement measurement, UriInfo uriInfo) {
		super(measurement.getId(), "ObservedProperties", uriInfo);
		
		m_measurement = measurement;
		setAttribute("URI", measurement.getUrn());
		setAttribute("UnitOfMeasurement", measurement.getUnit());
		addLink("Datastreams");
	}

	@Override
	public Stream<STTuple> navigate(String link) {
		if ( link.equalsIgnoreCase("Datastreams") ) {
			return m_measurement.streamOfSensorValueStream()
								.map(s -> new Datastream(s, m_uriInfo));
		}
		
		return super.navigate(link);
	}
}
