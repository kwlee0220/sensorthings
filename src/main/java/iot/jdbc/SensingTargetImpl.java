package iot.jdbc;

import iot.IoTPlatformException;
import iot.SensingTarget;
import iot.SensorValueLog;

import java.sql.SQLException;
import java.util.stream.Stream;

import com.vividsolutions.jts.geom.Geometry;




/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class SensingTargetImpl implements SensingTarget {
	private final IoTPlatformImpl m_platform;
	
	private final long m_id;
	private final Geometry m_geom;
	private final String m_desc;
	
	public SensingTargetImpl(IoTPlatformImpl platform, long id, Geometry geom, String desc) {
		m_platform = platform;
		
		m_id = id;
		m_geom = geom;
		m_desc = desc;
	}

	@Override
	public long getId() {
		return m_id;
	}

	@Override
	public Geometry getGeometry() {
		return m_geom;
	}
	
	@Override
	public String getDescription() {
		return m_desc;
	}

	@Override
	public Stream<SensorValueLog> streamOfSensorValueLog() {
		try {
			return m_platform.getStore().m_values.streamOfTarget(m_id)
										.map(SensorValueLog.class::cast);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}
}
