package iot;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public interface SensorValueSubscriber {
	public long getId();

	public String getChannel();
	
	public SensorInfo getSensor();
	public SensorValueStream getSensorValueStream();
	public SensingTarget getSensingTarget();

	public long update(int value);
}
