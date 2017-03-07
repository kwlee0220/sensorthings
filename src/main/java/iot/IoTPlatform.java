package iot;

import java.util.Map;
import java.util.stream.Stream;

import com.vividsolutions.jts.geom.Geometry;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public interface IoTPlatform {
	public SensorNode getSensorNode(long id);
	public SensorNodeLocation getSensorNodeLocation(long id);
	public SensorValueStream getSensorValueStream(long id);
	public SensorInfo getSensor(long id);
	public Measurement getMeasurement(long id);
	public SensingTarget getSensingTarget(long id);
	public SensorValueLog getSensorValueLog(long id);
	
	public SensorValueSubscriber getSensorValueSubscriber(long id);
	public SensorValueSubscriber getSensorValueSubscriberByChannel(String channel);

	public Stream<SensorNode> streamOfSensorNode();
	public Stream<SensorNodeLocation> streamOfSensorNodeLocation();
	public Stream<SensorValueStream> streamOfSensorValueStream();
	public Stream<SensorInfo> streamOfSensor();
	public Stream<SensingTarget> streamOfSensingTarget();
	public Stream<Measurement> streamOfMeasurement();
	public Stream<SensorValueLog> streamOfSensorValueLog();
	public Stream<SensorValueSubscriber> streamOfSensorValueSubscriber();
	
	public SensorNodeLocation createSensorNodeLocation(long nodeId, Geometry loc);
	public SensorNode createSensorNode(String desc);
	public SensorValueStream createSensorValueStream(long nodeId, long measurementId, String desc);
	public Measurement createMeasurement(String urn, String unit);
	public SensingTarget createSensingTarget(Geometry loc, String desc);
	public SensorInfo createSensor(String meta);
	public SensorValueSubscriber createSensorValueSubscriber(String channel, long streamId,
															long sensorId, long targetId);
	public long createSensorValueLog(long sensorId, long streamId, long targetId,
									String valueType, int value);
	
	public void deleteSensorNodeLocation(long locId);
	public void deleteSensorNode(long nodeId);
	public void deleteSensorValueStream(long nodeId);
	public void deleteMeasurement(long measurementId);
	public void deleteSensingTarget(long targetId);
	public void deleteSensor(long sensorId);
	public void deleteSensorValueSubscriber(long nodeId);
	public void deleteSensorValueLog(long logId);
	
	public void updateSensorNodeLocation(long id, Map<String,Object> udata);
	public void updateSensorNode(long id, Map<String,Object> udata);
	public void updateSensorValueStream(long id, Map<String,Object> udata);
	public void updateSensingTarget(long id, Map<String, Object> udata);
	public void updateMeasurement(long id, Map<String, Object> udata);
	public void updateSensorValueSubscriber(long id, Map<String, Object> udata);
}