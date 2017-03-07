package iot.restful;

import iot.SensorInfo;

import java.util.stream.Stream;

import javax.ws.rs.core.UriInfo;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class Sensor extends STNode {
	private final SensorInfo m_sensor;
	
	public Sensor(SensorInfo sensor, UriInfo uriInfo) {
		super(sensor.getId(), "Sensors", uriInfo);
		
		m_sensor = sensor;
		setAttribute("Metadata", sensor.getMetadata());
		addLink("Observations");
	}

	@Override
	public Stream<STTuple> navigate(String link) {
		if ( link.equalsIgnoreCase("Observations") ) {
			return m_sensor.streamOfSensorValueLog()
							.map(v -> new Observation(v, m_uriInfo));
		}
		
		return super.navigate(link);
	}
}
