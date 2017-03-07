package iot;

import java.util.stream.Stream;




/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public interface SensorInfo {
	public long getId();

	public String getMetadata();
	public Stream<SensorValueLog> streamOfSensorValueLog();
}
