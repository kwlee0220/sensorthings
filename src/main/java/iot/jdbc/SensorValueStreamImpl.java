package iot.jdbc;

import iot.IoTPlatformException;
import iot.Measurement;
import iot.SensorNode;
import iot.SensorValueLog;
import iot.SensorValueStream;

import java.sql.SQLException;
import java.util.stream.Stream;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class SensorValueStreamImpl implements SensorValueStream {
	private final IoTPlatformImpl m_platform;
	
	private final long m_id;
	private final long m_nodeId;
	private final long m_measurementId;
	private final String m_desc;
	
	public SensorValueStreamImpl(IoTPlatformImpl platform, long id, long nodeId,
								long measurementId, String desc) {
		m_platform = platform;
		m_id = id;
		m_nodeId = nodeId;
		m_measurementId = measurementId;
		m_desc = desc;
	}

	@Override
	public long getId() {
		return m_id;
	}
	
	public IoTPlatformImpl getPlatform() {
		return m_platform;
	}
	
	@Override
	public SensorNode getSensorNode() {
		return m_platform.getSensorNode(m_nodeId);
	}

	@Override
	public Measurement getMeasurement() {
		return m_platform.getMeasurement(m_measurementId);
	}

	@Override
	public String getDescription() {
		return m_desc;
	}

	@Override
	public Stream<SensorValueLog> streamOfValue() {
		try {
			return m_platform.getStore().m_values.streamOfSensorValueStream(m_id)
										.map(SensorValueLog.class::cast);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}
	
	@Override
	public String toString() {
		return String.format("%d", m_id);
	}
}
