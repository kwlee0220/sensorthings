package iot.jdbc;

import iot.IoTPlatformException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;
import java.util.stream.Stream;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class JDBCSensorValues {
	private static final String SQL_PREFIX = "SELECT id, sensor, stream, target, type, value, ts "
											+ "FROM SENSOR_VALUE_LOGS ";
	private static final String SQL_SUFFIX = " ORDER BY ts DESC";
	private final IoTPlatformImpl m_platform;

	private final Connection m_conn;
	private final PreparedStatement m_pstmtGet;
	private final PreparedStatement m_pstmtGetAll;
	private final PreparedStatement m_pstmtGetAllOfStream;
	private final PreparedStatement m_pstmtGetAllOfSensor;
	private final PreparedStatement m_pstmtGetAllOfTarget;
	private final PreparedStatement m_pstmtCreate;
	
	public JDBCSensorValues(IoTPlatformImpl platform, Connection conn) throws SQLException {
		m_platform = platform;
		m_conn = conn;
		
		// prepare all necessary SQL statements
		m_pstmtGet = conn.prepareStatement(SQL_PREFIX + "WHERE id=?" + SQL_SUFFIX);
		m_pstmtGetAll = conn.prepareStatement(SQL_PREFIX + SQL_SUFFIX);
		m_pstmtGetAllOfStream = conn.prepareStatement(SQL_PREFIX + "WHERE stream=?" + SQL_SUFFIX);
		m_pstmtGetAllOfSensor = conn.prepareStatement(SQL_PREFIX + "WHERE sensor=?" + SQL_SUFFIX);
		m_pstmtGetAllOfTarget = conn.prepareStatement(SQL_PREFIX + "WHERE target=?" + SQL_SUFFIX);
		m_pstmtCreate = m_conn.prepareStatement("INSERT INTO SENSOR_VALUE_LOGS "
												+ "VALUES (default, ?, ?, ?, ?, ?, default)");
	}
	
	SensorValueLogImpl get(long id) throws SQLException {
		m_pstmtGet.setLong(1, id);
		return ResultSetStream.getFirst(m_pstmtGet, m_mapper);
	}
	
	Stream<SensorValueLogImpl> stream() throws SQLException {
		return ResultSetStream.create(m_pstmtGetAll, m_mapper);
	}
	
	Stream<SensorValueLogImpl> streamOfSensorValueStream(long streamId) throws SQLException {
		m_pstmtGetAllOfStream.setLong(1, streamId);
		return ResultSetStream.create(m_pstmtGetAllOfStream, m_mapper);
	}
	
	Stream<SensorValueLogImpl> streamOfSensor(long sensorId) throws SQLException {
		m_pstmtGetAllOfSensor.setLong(1, sensorId);
		return ResultSetStream.create(m_pstmtGetAllOfSensor, m_mapper);
	}
	
	Stream<SensorValueLogImpl> streamOfTarget(long targetId)
		throws SQLException {
		m_pstmtGetAllOfTarget.setLong(1, targetId);
		return ResultSetStream.create(m_pstmtGetAllOfTarget, m_mapper);
	}

	long create(long sensorId, long streamId, long targetId, String valueType, int value)
		throws SQLException {
		m_pstmtCreate.setLong(1, sensorId);
		m_pstmtCreate.setLong(2, streamId);
		m_pstmtCreate.setLong(3, targetId);
		m_pstmtCreate.setString(4, valueType);
		m_pstmtCreate.setInt(5, value);
		if ( m_pstmtCreate.executeUpdate() != 1 ) {
			throw new IoTPlatformException("fails to insert a row into table \"SENSOR_VALUE_LOGS\"");
		}
		
		return JdbcUtils.getGeneratedKey(m_pstmtCreate);
	}
	
	void delete(long id) throws SQLException {
		Statement stmt = null;
		try {
			stmt = m_conn.createStatement();
			stmt.executeUpdate("DELETE FROM SENSOR_VALUE_LOGS WHERE id=" + id);
		}
		finally {
			JdbcUtils.closeQuietly(stmt);
		}
	}
	
	private Function<ResultSet,SensorValueLogImpl> m_mapper = new Function<ResultSet,SensorValueLogImpl>() {
		@Override
		public SensorValueLogImpl apply(ResultSet rs) {
			try {
				return new SensorValueLogImpl(m_platform, rs.getLong(1), rs.getLong(2),
											rs.getLong(3), rs.getLong(4), rs.getString(5),
											rs.getInt(6), rs.getTimestamp(7).toInstant());
			}
			catch ( SQLException e ) {
				return null;
			}
		}
	};
}
