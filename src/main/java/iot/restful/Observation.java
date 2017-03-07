package iot.restful;

import iot.SensorValueLog;

import java.util.stream.Stream;

import javax.ws.rs.core.UriInfo;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class Observation extends STNode {
	private final SensorValueLog m_value;
	
	public Observation(SensorValueLog value, UriInfo uriInfo) {
		super(value.getId(), "Observations", uriInfo);
		
		m_value = value;
		setAttribute("ResultType", value.getValueType());
		setAttribute("ResultValue", value.get());
		setAttribute("Time", value.getTimestamp());
		addLink("Datastream");
		addLink("Sensor");
		addLink("FeatureOfInterest");
	}

	@Override
	public Stream<STTuple> navigate(String link) {
		if ( link.equalsIgnoreCase("Datastream") ) {
			return Stream.of(new Datastream(m_value.getSensorValueStream(), m_uriInfo));
		}
		if ( link.equalsIgnoreCase("Sensor") ) {
			return Stream.of(new Sensor(m_value.getSensor(), m_uriInfo));
		}
		if ( link.equalsIgnoreCase("FeatureOfInterest") ) {
			return Stream.of(new FeatureOfInterest(m_value.getSensingTarget(), m_uriInfo));
		}
		
		return super.navigate(link);
	}
}
