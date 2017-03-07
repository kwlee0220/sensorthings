package iot.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import com.vividsolutions.jts.geom.Geometry;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class JDBCSensingTargets {
	private static final String SQL_PREFIX = "SELECT id, geom, desc FROM SENSING_TARGETS ";
	private final IoTPlatformImpl m_platform;
	
	private final Connection m_conn;
	private final PreparedStatement m_pstmtGet;
	private final PreparedStatement m_pstmtGetAll;
	
	public JDBCSensingTargets(IoTPlatformImpl platform, Connection conn) throws SQLException {
		m_platform = platform;
		m_conn = conn;
		
		// prepare all necessary SQL statements
		m_pstmtGet = conn.prepareStatement(SQL_PREFIX + "WHERE id=?");
		m_pstmtGetAll = conn.prepareStatement(SQL_PREFIX);
	}
	
	SensingTargetImpl get(long id) throws SQLException {
		m_pstmtGet.setLong(1, id);
		return ResultSetStream.getFirst(m_pstmtGet, m_mapper);
	}
	
	Stream<SensingTargetImpl> stream() throws SQLException {
		return ResultSetStream.create(m_pstmtGetAll, m_mapper);
	}

	long create(Geometry geom, String desc) throws SQLException {
		PreparedStatement pstmt =null;
		
		try {
			pstmt = m_conn.prepareStatement("INSERT INTO SENSING_TARGETS VALUES (default, ?, ?)");
			pstmt.setObject(1, geom);
			pstmt.setString(2, desc);
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
			stmt.executeUpdate("DELETE FROM SENSING_TARGETS WHERE id=" + id);
		}
		finally {
			JdbcUtils.closeQuietly(stmt);
		}
	}
	
	void update(long id, Map<String, Object> udata) throws SQLException {
		JdbcUtils.updateTable(m_conn, "SENSING_TARGETS", id, udata);
	}
	
	private Function<ResultSet,SensingTargetImpl> m_mapper = new Function<ResultSet,SensingTargetImpl>() {
		@Override
		public SensingTargetImpl apply(ResultSet rs) {
			try {
				return new SensingTargetImpl(m_platform, rs.getLong(1), (Geometry)rs.getObject(2),
											rs.getString(3));
			}
			catch ( SQLException e ) {
				return null;
			}
		}
	};
}
