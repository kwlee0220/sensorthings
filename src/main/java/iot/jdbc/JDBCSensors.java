package iot.jdbc;

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
public class JDBCSensors {
	private static final String SQL_PREFIX = "SELECT id, meta FROM SENSOR_INFOS ";
	private final IoTPlatformImpl m_platform;
	
	private final Connection m_conn;
	private final PreparedStatement m_pstmtGet;
	private final PreparedStatement m_pstmtGetAll;
	
	public JDBCSensors(IoTPlatformImpl platform, Connection conn) throws SQLException {
		m_platform = platform;
		m_conn = conn;
		
		// prepare all necessary SQL statements
		m_pstmtGet = conn.prepareStatement(SQL_PREFIX + "WHERE id=?");
		m_pstmtGetAll = conn.prepareStatement(SQL_PREFIX);
	}
	
	SensorInstanceImpl get(long id) throws SQLException {
		m_pstmtGet.setLong(1, id);
		return ResultSetStream.getFirst(m_pstmtGet, m_mapper);
	}
	
	Stream<SensorInstanceImpl> stream() throws SQLException {
		return ResultSetStream.create(m_pstmtGetAll, m_mapper);
	}

	long create(String meta) throws SQLException {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = m_conn.prepareStatement("INSERT INTO SENSOR_INFOS VALUES (default, ?)");
			pstmt.setString(1, meta);
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
			stmt.executeUpdate("DELETE FROM SENSOR_INFOS WHERE id=" + locId);
		}
		finally {
			JdbcUtils.closeQuietly(stmt);
		}
	}
	
	private Function<ResultSet,SensorInstanceImpl> m_mapper = new Function<ResultSet,SensorInstanceImpl>() {
		@Override
		public SensorInstanceImpl apply(ResultSet rs) {
			try {
				return new SensorInstanceImpl(m_platform, rs.getLong(1), rs.getString(2));
			}
			catch ( SQLException e ) {
				return null;
			}
		}
	};
}
