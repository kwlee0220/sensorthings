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
public class JDBCMeasurements {
	private static final String SQL_PREFIX = "SELECT id, urn, unit FROM MEASUREMENTS ";
	private final IoTPlatformImpl m_platform;
	
	private final Connection m_conn;
	private final PreparedStatement m_pstmtGet;
	private final PreparedStatement m_pstmtGetAll;
	
	public JDBCMeasurements(IoTPlatformImpl platform, Connection conn) throws SQLException {
		m_platform = platform;
		m_conn = conn;
		
		// prepare all necessary SQL statements
		m_pstmtGet = conn.prepareStatement(SQL_PREFIX + "WHERE id=?");
		m_pstmtGetAll = conn.prepareStatement(SQL_PREFIX);
	}
	
	MeasurementImpl get(long id) throws SQLException {
		m_pstmtGet.setLong(1, id);
		return ResultSetStream.getFirst(m_pstmtGet, m_mapper);
	}
	
	Stream<MeasurementImpl> stream() throws SQLException {
		return ResultSetStream.create(m_pstmtGetAll, m_mapper);
	}

	long create(String urn, String unit) throws SQLException {
		PreparedStatement pstmt =null;
		
		try {
			pstmt = m_conn.prepareStatement("INSERT INTO MEASUREMENTS VALUES (default, ?, ?)");
			pstmt.setString(1, urn);
			pstmt.setString(2, unit);
			pstmt.executeUpdate();
			
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
			stmt.executeUpdate("DELETE FROM MEASUREMENTS WHERE id=" + id);
		}
		finally {
			JdbcUtils.closeQuietly(stmt);
		}
	}
	
	void update(long id, Map<String, Object> udata) throws SQLException {
		JdbcUtils.updateTable(m_conn, "MEASUREMENTS", id, udata);
	}
	
	private Function<ResultSet,MeasurementImpl> m_mapper = new  Function<ResultSet,MeasurementImpl>() {
		@Override
		public MeasurementImpl apply(ResultSet rs) {
			try {
				return new MeasurementImpl(m_platform, rs.getLong(1), rs.getString(2),
											rs.getString(3));
			}
			catch ( SQLException e ) {
				return null;
			}
		}
	};
}
