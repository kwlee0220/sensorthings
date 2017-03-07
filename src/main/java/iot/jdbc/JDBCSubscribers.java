package iot.jdbc;

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
public class JDBCSubscribers {
	private static final String SQL_PREFIX = "SELECT id, channel, stream, sensor, target FROM SUBSCRIBERS ";
	private final IoTPlatformImpl m_platform;
	
	private final Connection m_conn;
	private final PreparedStatement m_pstmtGet;
	private final PreparedStatement m_pstmtGetAll;
	private final PreparedStatement m_pstmtGetAllOfChannel;
	
	public JDBCSubscribers(IoTPlatformImpl platform, Connection conn) throws SQLException {
		m_platform = platform;
		m_conn = conn;
		
		// prepare all necessary SQL statements
		m_pstmtGet = conn.prepareStatement(SQL_PREFIX + "WHERE id=?");
		m_pstmtGetAll = conn.prepareStatement(SQL_PREFIX);
		m_pstmtGetAllOfChannel = conn.prepareStatement(SQL_PREFIX + "WHERE channel=?");
	}
	
	SensorValueSubscriberImpl get(long id) throws SQLException {
		m_pstmtGet.setLong(1, id);
		return ResultSetStream.getFirst(m_pstmtGet, m_mapper);
	}
	
	SensorValueSubscriberImpl getByChannel(String channel) throws SQLException {
		m_pstmtGetAllOfChannel.setString(1, channel);
		return ResultSetStream.getFirst(m_pstmtGetAllOfChannel, m_mapper);
	}
	
	Stream<SensorValueSubscriberImpl> stream() throws SQLException {
		return ResultSetStream.create(m_pstmtGetAll, m_mapper);
	}

	long create(String channel, long streamId, long sensorId, long targetId) throws SQLException {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = m_conn.prepareStatement("INSERT INTO SUBSCRIBERS VALUES (default, ?, ?, ?, ?)");
			pstmt.setString(1, channel);
			pstmt.setLong(2, streamId);
			pstmt.setLong(3, sensorId);
			pstmt.setLong(4, targetId);
			pstmt.executeUpdate();
			
			return JdbcUtils.getGeneratedKey(pstmt);
		}
		finally {
			JdbcUtils.closeQuietly(pstmt);
		}
	}
	
	void delete(long locId) throws SQLException {
		Statement stmt = null;
		try {
			stmt = m_conn.createStatement();
			stmt.executeUpdate("DELETE FROM SUBSCRIBERS WHERE id=" + locId);
		}
		finally {
			JdbcUtils.closeQuietly(stmt);
		}
	}
	
	void update(long id, Map<String, Object> udata) throws SQLException {
		JdbcUtils.updateTable(m_conn, "SUBSCRIBERS", id, udata);
	}
	
	private Function<ResultSet,SensorValueSubscriberImpl> m_mapper = new Function<ResultSet,SensorValueSubscriberImpl>() {
		@Override
		public SensorValueSubscriberImpl apply(ResultSet rs) {
			try {
				return new SensorValueSubscriberImpl(m_platform, rs.getLong(1), rs.getString(2),
									rs.getLong(3), rs.getLong(4), rs.getLong(5));
			}
			catch ( SQLException e ) {
				return null;
			}
		}
	};
}
