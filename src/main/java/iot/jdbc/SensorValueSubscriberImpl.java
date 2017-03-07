package iot.jdbc;

import iot.SensingTarget;
import iot.SensorInfo;
import iot.SensorValueStream;
import iot.SensorValueSubscriber;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class SensorValueSubscriberImpl implements SensorValueSubscriber {
	private final IoTPlatformImpl m_platform;
	
	private final long m_id;
	private final String m_channel;
	private final long m_streamId;
	private final long m_sensorId;
	private final long m_targetId;
	
	public SensorValueSubscriberImpl(IoTPlatformImpl platform, long id, String channel, long streamId,
								long sensorId, long targetId) {
		m_platform = platform;
		m_id = id;
		m_channel = channel;
		m_streamId = streamId;
		m_sensorId = sensorId;
		m_targetId = targetId;
	}

	@Override
	public long getId() {
		return m_id;
	}
	
	public IoTPlatformImpl getPlatform() {
		return m_platform;
	}

	@Override
	public String getChannel() {
		return m_channel;
	}

	@Override
	public SensorInfo getSensor() {
		return m_platform.getSensor(m_sensorId);
	}

	@Override
	public SensingTarget getSensingTarget() {
		return (m_targetId>=0) ? m_platform.getSensingTarget(m_targetId) : null;
	}

	@Override
	public SensorValueStream getSensorValueStream() {
		return m_platform.getSensorValueStream(m_streamId);
	}

	@Override
	public long update(int value) {
		return m_platform.createSensorValueLog(m_id, m_streamId, m_targetId, "Measured", value);
	}
	
	@Override
	public String toString() {
		return String.format("%d:%s", m_id, m_channel);
	}
}
