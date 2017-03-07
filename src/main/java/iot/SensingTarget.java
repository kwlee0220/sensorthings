package iot;

import java.util.stream.Stream;

import com.vividsolutions.jts.geom.Geometry;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public interface SensingTarget {
	public long getId();
	public Geometry getGeometry();
	public String getDescription();

	public Stream<SensorValueLog> streamOfSensorValueLog();
}