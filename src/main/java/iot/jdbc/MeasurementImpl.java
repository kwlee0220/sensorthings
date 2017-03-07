package iot.jdbc;

import iot.IoTPlatformException;
import iot.Measurement;
import iot.SensorValueStream;

import java.sql.SQLException;
import java.util.stream.Stream;




/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class MeasurementImpl implements Measurement {
	private final IoTPlatformImpl m_platform;
	
	private final long m_id;
	private final String m_urn;
	private final String m_unit;
	
	public MeasurementImpl(IoTPlatformImpl platform, long id, String urn, String unit) {
		m_platform = platform;
		
		m_id = id;
		m_urn = urn;
		m_unit = unit;
	}

	@Override
	public long getId() {
		return m_id;
	}

	@Override
	public String getUrn() {
		return m_urn;
	}

	@Override
	public String getUnit() {
		return m_unit;
	}

	@Override
	public Stream<SensorValueStream> streamOfSensorValueStream() {
		try {
			return m_platform.getStore().m_streams.streamOfMeasurement(m_id)
										.map(SensorValueStream.class::cast);
		}
		catch ( SQLException e ) {
			throw new IoTPlatformException(e);
		}
	}
}
