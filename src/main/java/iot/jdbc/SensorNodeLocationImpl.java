package iot.jdbc;

import iot.IoTPlatformException;
import iot.SensorNode;
import iot.SensorNodeLocation;

import java.sql.SQLException;
import java.time.Instant;
import java.util.stream.Stream;

import com.vividsolutions.jts.geom.Geometry;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class SensorNodeLocationImpl implements SensorNodeLocation {
	private final IoTPlatformImpl m_platform;
	
	private final long m_id;
	private Geometry m_geom;
	private Instant m_ts;
	
	SensorNodeLocationImpl(IoTPlatformImpl platform, long id, Geometry geom, Instant ts) {
		m_platform = platform;
		m_id = id;
		m_geom = geom;
		m_ts = ts;
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
	public void setGeometry(Geometry geom) {
		m_geom = geom;
	}

	@Override
	public Instant getTimestamp() {
		return m_ts;
	}

	@Override
	public void setTimestamp(Instant ts) {
		m_ts = ts;
	}

	@Override
	public Stream<SensorNode> getSensorNodeAll() {
		try {
			return m_platform.getStore().m_nodes.streamAtLocation(m_id)
										.map(node -> (SensorNode)node);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}
}
