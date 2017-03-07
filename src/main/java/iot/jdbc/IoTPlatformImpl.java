package iot.jdbc;

import iot.IoTPlatform;
import iot.IoTPlatformException;
import iot.Measurement;
import iot.SensingTarget;
import iot.SensorInfo;
import iot.SensorNode;
import iot.SensorNodeLocation;
import iot.SensorValueLog;
import iot.SensorValueStream;
import iot.SensorValueSubscriber;

import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Stream;

import jersey.repackaged.com.google.common.base.Preconditions;

import com.vividsolutions.jts.geom.Geometry;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class IoTPlatformImpl implements IoTPlatform {
	// properties (BEGIN)
	private volatile String m_jdbcUrl;
	// properties (END)
	
	private JDBCStore m_store;
	
	public final void setJdbcUrl(String url) {
		m_jdbcUrl = url;
	}

	public void initialize() throws Exception {
		Preconditions.checkNotNull(m_jdbcUrl, "Property 'jdbcUrl' has not bee set: class="
											+ getClass().getName());
		
		m_store = new JDBCStore(this, m_jdbcUrl, "sa");
	}

	public void destroy() throws Exception {
		m_store.close();
	}
	
	JDBCStore getStore() {
		return m_store;
	}

	@Override
	public SensorNodeImpl getSensorNode(long id) {
		try {
			return m_store.m_nodes.get(id);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public SensorNodeLocationImpl getSensorNodeLocation(long id) {
		try {
			return m_store.m_locations.get(id);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public SensorValueStreamImpl getSensorValueStream(long id) {
		try {
			return m_store.m_streams.get(id);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public SensorInstanceImpl getSensor(long id) {
		try {
			return m_store.m_sensors.get(id);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public SensorValueSubscriberImpl getSensorValueSubscriber(long id) {
		try {
			return m_store.m_subscribers.get(id);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public SensorValueSubscriberImpl getSensorValueSubscriberByChannel(String channel) {
		try {
			return m_store.m_subscribers.getByChannel(channel);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public MeasurementImpl getMeasurement(long id) {
		try {
			return m_store.m_measurements.get(id);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public SensingTargetImpl getSensingTarget(long id) {
		try {
			return m_store.m_targets.get(id);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}
	
	@Override
	public SensorValueLogImpl getSensorValueLog(long id) {
		try {
			return m_store.m_values.get(id);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public Stream<SensorNode> streamOfSensorNode() {
		try {
			return m_store.m_nodes.stream().map(SensorNode.class::cast);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public Stream<SensorNodeLocation> streamOfSensorNodeLocation() {
		try {
			return m_store.m_locations.stream().map(SensorNodeLocation.class::cast);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}
	
	@Override
	public Stream<SensorValueStream> streamOfSensorValueStream() {
		try {
			return m_store.m_streams.stream().map(SensorValueStream.class::cast);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}
	
	@Override
	public Stream<SensorInfo> streamOfSensor() {
		try {
			return m_store.m_sensors.stream().map(SensorInfo.class::cast);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}
	
	@Override
	public Stream<Measurement> streamOfMeasurement() {
		try {
			return m_store.m_measurements.stream().map(Measurement.class::cast);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}
	
	@Override
	public Stream<SensingTarget> streamOfSensingTarget() {
		try {
			return m_store.m_targets.stream().map(SensingTarget.class::cast);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}
	
	@Override
	public Stream<SensorValueLog> streamOfSensorValueLog() {
		try {
			return m_store.m_values.stream().map(SensorValueLog.class::cast);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}
	
	@Override
	public Stream<SensorValueSubscriber> streamOfSensorValueSubscriber() {
		try {
			return m_store.m_subscribers.stream().map(SensorValueSubscriber.class::cast);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public SensorNode createSensorNode(String desc) {
		try {
			long id = m_store.m_nodes.create(desc);
			return m_store.m_nodes.get(id);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public SensorNodeLocation createSensorNodeLocation(long nodeId, Geometry loc) {
		try {
			long id = m_store.m_locations.create(nodeId, loc);
			return m_store.m_locations.get(id);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public SensorValueStream createSensorValueStream(long nodeId, long measurementId, String desc) {
		try {
			long id = m_store.m_streams.create(nodeId, measurementId, desc);
			return m_store.m_streams.get(id);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public Measurement createMeasurement(String urn, String unit) {
		try {
			long id = m_store.m_measurements.create(urn, unit);
			return m_store.m_measurements.get(id);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public SensingTarget createSensingTarget(Geometry loc, String desc) {
		try {
			long id = m_store.m_targets.create(loc, desc);
			return m_store.m_targets.get(id);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public SensorInfo createSensor(String meta) {
		try {
			long id = m_store.m_sensors.create(meta);
			return m_store.m_sensors.get(id);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public SensorValueSubscriber createSensorValueSubscriber(String channel, long streamId,
															long sensorId, long targetId) {
		try {
			long id = m_store.m_subscribers.create(channel, streamId, sensorId, targetId);
			return m_store.m_subscribers.get(id);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public long createSensorValueLog(long sensorId, long streamId, long targetId,
									String valueType, int value) {
		try {
			if ( valueType == null ) {
				valueType = "Measure";
			}
			return m_store.m_values.create(sensorId, streamId, targetId, valueType, value);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException("fails to update table SENSORS, cause=" + e);
		}
	}

	@Override
	public void deleteSensorNode(long nodeId) {
		try {
			m_store.m_nodes.delete(nodeId);
			m_store.m_locations.deleteObsoleteAll();
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public void deleteSensorNodeLocation(long locId) {
		try {
			m_store.m_locations.delete(locId);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public void deleteSensorValueStream(long streamId) {
		try {
			m_store.m_streams.delete(streamId);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public void deleteMeasurement(long measurementId) {
		try {
			m_store.m_measurements.delete(measurementId);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public void deleteSensingTarget(long targetId) {
		try {
			m_store.m_targets.delete(targetId);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public void deleteSensor(long sensorId) {
		try {
			m_store.m_sensors.delete(sensorId);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public void deleteSensorValueSubscriber(long logId) {
		try {
			m_store.m_subscribers.delete(logId);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public void deleteSensorValueLog(long logId) {
		try {
			m_store.m_values.delete(logId);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public void updateSensorNode(long id, Map<String, Object> udata) {
		try {
			m_store.m_nodes.update(id, udata);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public void updateSensorNodeLocation(long id, Map<String, Object> udata) {
		try {
			m_store.m_locations.update(id, udata);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public void updateSensorValueStream(long id, Map<String, Object> udata) {
		try {
			m_store.m_streams.update(id, udata);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public void updateSensingTarget(long id, Map<String, Object> udata) {
		try {
			m_store.m_targets.update(id, udata);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public void updateMeasurement(long id, Map<String, Object> udata) {
		try {
			m_store.m_measurements.update(id, udata);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}

	@Override
	public void updateSensorValueSubscriber(long id, Map<String, Object> udata) {
		try {
			m_store.m_subscribers.update(id, udata);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}
}