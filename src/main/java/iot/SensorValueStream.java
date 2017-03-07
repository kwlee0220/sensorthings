package iot;

import java.util.stream.Stream;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public interface SensorValueStream {
	public long getId();
	
	public SensorNode getSensorNode();
	public Measurement getMeasurement();
	public String getDescription();
	
	public Stream<SensorValueLog> streamOfValue();
}
