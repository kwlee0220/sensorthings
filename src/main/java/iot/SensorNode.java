package iot;

import java.util.stream.Stream;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public interface SensorNode {
	public long getId();
	public String getDescription();
	
	public SensorNodeLocation getLastLocation();
	public Stream<SensorNodeLocation> streamOfLocation();
	
	public Stream<SensorValueStream> streamOfSensorValueStreamAll();
}
