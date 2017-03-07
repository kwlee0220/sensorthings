package iot;

import java.time.Instant;
import java.util.stream.Stream;

import com.vividsolutions.jts.geom.Geometry;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public interface SensorNodeLocation {
	public long getId();
	public Geometry getGeometry();
	public Instant getTimestamp();
	
	public void setGeometry(Geometry geom);
	public void setTimestamp(Instant ts);
	
	public Stream<SensorNode> getSensorNodeAll();
}
