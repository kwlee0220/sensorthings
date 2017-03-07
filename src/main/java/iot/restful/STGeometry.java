package iot.restful;

import java.util.Map;
import java.util.stream.Stream;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;




/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class STGeometry extends STTuple {
	private final Geometry m_geom;
	
	public STGeometry(Geometry geom) {
		m_geom = geom;
	}
	
	public Geometry getGeometry() {
		return m_geom;
	}

	@Override
	public Map<String, Object> serialize() {
		return SensorThingsContext.getContext().toGeoJsonMap(m_geom);
	}

	@Override
	public Stream<STTuple> navigate(String link) {
		if ( link.equals("type") ) {
			if ( m_geom instanceof Point ) {
				return Stream.of(new STTuple("type", "Point"));
			}
			if ( m_geom instanceof LineString ) {
				return Stream.of(new STTuple("type", "LineString"));
			}
			if ( m_geom instanceof Polygon ) {
				return Stream.of(new STTuple("type", "Polygon"));
			}
		}
		else if ( link.equals("coordinates") ) {
			SensorThingsContext context = SensorThingsContext.getContext();
			
			Map<String,Object> data = context.toGeoJsonMap(m_geom);
			return Stream.of(new STTuple("coordinates", data.get("coordinates")));
		}
		
		return Stream.empty();
	}
	
	@Override
	public String toString() {
		return m_geom.toString();
	}
}
