package iot.jdbc;

import iot.IoTPlatformException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class JDBCSensorValueStreams {
	private static final String SQL_PREFIX = "SELECT id, node, measurement, desc FROM SENSOR_VALUE_STREAMS ";
	private final IoTPlatformImpl m_platform;

	private final Connection m_conn;
	private final PreparedStatement m_pstmtGet;
	private final PreparedStatement m_pstmtGetAll;
	private final PreparedStatement m_pstmtGetAllOfNode;
	private final PreparedStatement m_pstmtGetAllOfMeasurement;
	
	public JDBCSensorValueStreams(IoTPlatformImpl platform, Connection conn) throws SQLException {
		m_platform = platform;
		m_conn = conn;
		
		// prepare all necessary SQL statements
		m_pstmtGet = conn.prepareStatement(SQL_PREFIX + "WHERE id=?");
		m_pstmtGetAll = conn.prepareStatement(SQL_PREFIX);
		m_pstmtGetAllOfNode = conn.prepareStatement(SQL_PREFIX + "WHERE node=?");
		m_pstmtGetAllOfMeasurement = conn.prepareStatement(SQL_PREFIX + "WHERE measurement=?");
	}
	
	SensorValueStreamImpl get(long id) throws SQLException {
		m_pstmtGet.setLong(1, id);
		return ResultSetStream.getFirst(m_pstmtGet, m_mapper);
	}
	
	Stream<SensorValueStreamImpl> stream() throws SQLException {
		return ResultSetStream.create(m_pstmtGetAll, m_mapper);
	}
	
	Stream<SensorValueStreamImpl> streamOfNode(long nodeId) throws SQLException {
		m_pstmtGetAllOfNode.setLong(1, nodeId);
		return ResultSetStream.create(m_pstmtGetAllOfNode, m_mapper);
	}
	
	Stream<SensorValueStreamImpl> streamOfMeasurement(long measurementId)
		throws SQLException {
		m_pstmtGetAllOfMeasurement.setLong(1, measurementId);
		return ResultSetStream.create(m_pstmtGetAllOfMeasurement, m_mapper);
	}

	long create(long nodeId, long measurementId, String desc) throws SQLException {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = m_conn.prepareStatement("INSERT INTO SENSOR_VALUE_STREAMS VALUES (default, ?, ?, ?)");
			pstmt.setLong(1, nodeId);
			pstmt.setLong(2, measurementId);
			pstmt.setString(3, desc);
			
			if ( pstmt.executeUpdate() != 1 ) {
				throw new IoTPlatformException("fails to insert a row into SENSOR_VALUE_STREAMS");
			}
			return JdbcUtils.getGeneratedKey(pstmt);
		}
		finally {
			JdbcUtils.closeQuietly(pstmt);
		}
	}
	
	void delete(long id) throws SQLException {
		Statement stmt = null;
		try {
			stmt = m_conn.createStatement();
			stmt.executeUpdate("DELETE FROM SENSOR_VALUE_STREAMS WHERE id=" + id);
		}
		finally {
			JdbcUtils.closeQuietly(stmt);
		}
	}
	
	void deleteAllOfSensorNode(long nodeId) throws SQLException {
		Statement stmt = null;
		try {
			stmt = m_conn.createStatement();
			stmt.executeUpdate("DELETE FROM SENSOR_VALUE_STREAMS WHERE node=" + nodeId);
		}
		finally {
			JdbcUtils.closeQuietly(stmt);
		}
	}
	
	void update(long id, Map<String, Object> udata) throws SQLException {
		JdbcUtils.updateTable(m_conn, "SENSOR_VALUE_STREAMS", id, udata);
	}
	
	private Function<ResultSet,SensorValueStreamImpl> m_mapper = new Function<ResultSet,SensorValueStreamImpl>() {
		@Override
		public SensorValueStreamImpl apply(ResultSet rs) {
			try {
				return new SensorValueStreamImpl(m_platform, rs.getLong(1), rs.getLong(2),
												rs.getLong(3), rs.getString(4));
			}
			catch ( SQLException e ) {
				return null;
			}
		}
	};
}
