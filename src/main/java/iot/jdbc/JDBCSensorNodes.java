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
public class JDBCSensorNodes {
	private static final String SQL_PREFIX = "SELECT id, desc FROM NODES ";
	private final IoTPlatformImpl m_platform;

	private final Connection m_conn;
	private final PreparedStatement m_pstmtGet;
	private final PreparedStatement m_pstmtGetAll;
	private final PreparedStatement m_pstmtGetAllAtLoc;
	
	public JDBCSensorNodes(IoTPlatformImpl platform, Connection conn) throws SQLException {
		m_platform = platform;
		m_conn = conn;
		
		// prepare all necessary SQL statements
		m_pstmtGet = conn.prepareStatement(SQL_PREFIX + "WHERE id=?");
		m_pstmtGetAll = conn.prepareStatement(SQL_PREFIX);
		m_pstmtGetAllAtLoc = conn.prepareStatement(SQL_PREFIX + "n, NODE_TO_LOCATION n2l "
														+ "WHERE n.id = n2l.node AND n2l.loc = ?");
	}
	
	SensorNodeImpl get(long id) throws SQLException {
		m_pstmtGet.setLong(1, id);
		return ResultSetStream.getFirst(m_pstmtGet, m_mapper);
	}
	
	Stream<SensorNodeImpl> stream() throws SQLException {
		return ResultSetStream.create(m_pstmtGetAll, m_mapper);
	}
	
	Stream<SensorNodeImpl> streamAtLocation(long locId) throws SQLException {
		m_pstmtGetAllAtLoc.setLong(1, locId);
		return ResultSetStream.create(m_pstmtGetAllAtLoc, m_mapper);
	}

	long create(String desc) throws SQLException {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = m_conn.prepareStatement("INSERT INTO NODES VALUES (default, ?)");
			pstmt.setString(1, desc);
			
			if ( pstmt.executeUpdate() != 1 ) {
				throw new IoTPlatformException("fails to insert a row into NODES");
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
			stmt.executeUpdate("DELETE FROM NODES WHERE id=" + id);
		}
		finally {
			JdbcUtils.closeQuietly(stmt);
		}
	}
	
	void update(long id, Map<String, Object> udata) throws SQLException {
		JdbcUtils.updateTable(m_conn, "NODES", id, udata);
	}
	
	private Function<ResultSet,SensorNodeImpl> m_mapper = new Function<ResultSet,SensorNodeImpl>() {
		@Override
		public SensorNodeImpl apply(ResultSet rs) {
			try {
				return new SensorNodeImpl(m_platform, rs.getLong(1), rs.getString(2));
			}
			catch ( SQLException e ) {
				return null;
			}
		}
	};
}
