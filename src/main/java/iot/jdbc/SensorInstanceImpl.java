package iot.jdbc;

import iot.IoTPlatformException;
import iot.SensorInfo;
import iot.SensorValueLog;

import java.sql.SQLException;
import java.util.stream.Stream;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class SensorInstanceImpl implements SensorInfo {
	private final IoTPlatformImpl m_platform;
	
	private final long m_id;
	private final String m_meta;
	
	public SensorInstanceImpl(IoTPlatformImpl platform, long id, String meta) {
		m_platform = platform;
		m_id = id;
		m_meta = meta;
	}

	@Override
	public long getId() {
		return m_id;
	}
	
	public IoTPlatformImpl getPlatform() {
		return m_platform;
	}

	@Override
	public String getMetadata() {
		return m_meta;
	}

	@Override
	public Stream<SensorValueLog> streamOfSensorValueLog() {
		try {
			return m_platform.getStore().m_values.streamOfSensor(m_id)
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
