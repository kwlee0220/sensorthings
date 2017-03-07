package iot;

import java.time.Instant;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public interface SensorValueLog {
	public long getId();

	public SensorValueStream getSensorValueStream();
	public SensorInfo getSensor();
	public SensingTarget getSensingTarget();
	
	public int get();
	public String getValueType();
	public Instant getTimestamp();
}
