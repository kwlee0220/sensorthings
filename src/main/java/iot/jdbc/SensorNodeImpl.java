package iot.jdbc;

import iot.IoTPlatformException;
import iot.SensorNode;
import iot.SensorNodeLocation;
import iot.SensorValueStream;

import java.sql.SQLException;
import java.util.stream.Stream;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class SensorNodeImpl implements SensorNode {
	private IoTPlatformImpl m_platform;
	
	private final long m_id;
	private final String m_desc;
	
	public SensorNodeImpl(IoTPlatformImpl platform, long id, String desc) {
		m_platform = platform;
		
		m_id = id;
		m_desc = desc;
	}
	
	@Override
	public long getId() {
		return m_id;
	}

	@Override
	public String getDescription() {
		return m_desc;
	}

	@Override
	public SensorNodeLocation getLastLocation() {
		return streamOfLocation().findFirst().orElse(null);
	}

	@Override
	public Stream<SensorNodeLocation> streamOfLocation() {
		try {
			return m_platform.getStore().m_locations.streamOfNode(m_id)
										.map(SensorNodeLocation.class::cast);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public Stream<SensorValueStream> streamOfSensorValueStreamAll() {
		try {
			return m_platform.getStore().m_streams.streamOfNode(m_id)
										.map(SensorValueStream.class::cast);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}
	
	@Override
	public String toString() {
		return String.format("%s", m_id);
	}
	
	String toString(String sensorId) {
		return String.format("%s:%s", m_id, sensorId);
	}
}
