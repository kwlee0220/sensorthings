package iot;

import java.util.stream.Stream;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public interface Measurement {
	public long getId();
	
	public String getUrn();
	public String getUnit();
	
	public Stream<SensorValueStream> streamOfSensorValueStream();
}
