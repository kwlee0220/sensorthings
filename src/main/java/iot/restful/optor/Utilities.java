package iot.restful.optor;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public final class Utilities {
	
	public static double toDouble(Object vobj) {
		if ( vobj instanceof Long ) {
			return (double)((Long)vobj).longValue();
		}
		else if ( vobj instanceof Double ) {
			return ((Double)vobj).doubleValue();
		}
		else if ( vobj instanceof Integer ) {
			return (double)((Integer)vobj).intValue();
		}
		
		throw new RuntimeException("invalid numeric value=" + vobj);
	}
	
	public static long toLong(Object vobj) {
		if ( vobj instanceof Long ) {
			return ((Long)vobj).longValue();
		}
		else if ( vobj instanceof Integer ) {
			return ((Integer)vobj).intValue();
		}
		
		throw new RuntimeException("invalid integer value=" + vobj);
	}
	
	public static Geometry toGeometry(String wkt) throws ParseException {
		return new WKTReader().read(wkt);
	}
}
