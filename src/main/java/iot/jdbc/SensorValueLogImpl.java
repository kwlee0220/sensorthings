package iot.jdbc;

import iot.SensingTarget;
import iot.SensorInfo;
import iot.SensorValueLog;
import iot.SensorValueStream;

import java.time.Instant;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class SensorValueLogImpl implements SensorValueLog {
	private final IoTPlatformImpl m_platform;
	
	private final long m_id;
	private final long m_streamId;
	private final long m_sensorId;
	private final long m_targetId;
	private final String m_valueType;
	private final int m_value;
	private final Instant m_ts;
	
	public SensorValueLogImpl(IoTPlatformImpl platform, long id, long streamId, long sensorId,
								long targetId, String valueType, int value, Instant ts) {
		m_platform = platform;
		
		m_id = id;
		m_streamId = streamId;
		m_sensorId = sensorId;
		m_targetId = targetId;
		m_valueType = valueType;
		m_value = value;
		m_ts = ts;
	}
	
	public IoTPlatformImpl getPlatform() {
		return m_platform;
	}
	
	@Override
	public long getId() {
		return m_id;
	}
	
	public long getSensorValueStreamId() {
		return m_streamId;
	}

	@Override
	public SensorValueStream getSensorValueStream() {
		return m_platform.getSensorValueStream(m_streamId);
	}
	
	public long getSensorTypeId() {
		return m_sensorId;
	}

	@Override
	public SensorInfo getSensor() {
		return m_platform.getSensor(m_sensorId);
	}

	@Override
	public String getValueType() {
		return m_valueType;
	}

	@Override
	public SensingTarget getSensingTarget() {
		return m_platform.getSensingTarget(m_targetId);
	}

	@Override
	public int get() {
		return m_value;
	}

	@Override
	public Instant getTimestamp() {
		return m_ts;
	}
	
	@Override
	public String toString() {
		return String.format("%d:%d:%d", m_streamId, m_id, m_value);
	}
}
