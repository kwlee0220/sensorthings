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
public class JDBCSensorNodeLocation {
	private static final String SQL_PREFIX = "SELECT id, geom, ts FROM LOCATIONS ";
	private final IoTPlatformImpl m_platform;
	
	private final Connection m_conn;
	private final PreparedStatement m_pstmtGet;
	private final PreparedStatement m_pstmtGetAll;
	private final PreparedStatement m_pstmtGetAllOfNode;
	
	public JDBCSensorNodeLocation(IoTPlatformImpl platform, Connection conn) throws SQLException {
		m_platform = platform;
		m_conn = conn;
		
		// prepare all necessary SQL statements
		m_pstmtGet = conn.prepareStatement(SQL_PREFIX + "WHERE id=?");
		m_pstmtGetAll = conn.prepareStatement(SQL_PREFIX);
		m_pstmtGetAllOfNode = conn.prepareStatement(SQL_PREFIX + " l, NODE_TO_LOCATION n2l  "
															+ "WHERE l.id = n2l.node "
															+ "AND n2l.node = ? "
															+ "ORDER BY l.ts DESC");
	}
	
	SensorNodeLocationImpl get(long id) throws SQLException {
		m_pstmtGet.setLong(1, id);
		return ResultSetStream.getFirst(m_pstmtGet, m_mapper);
	}
	
	Stream<SensorNodeLocationImpl> stream() throws SQLException {
		return ResultSetStream.create(m_pstmtGetAll, m_mapper);
	}
	
	Stream<SensorNodeLocationImpl> streamOfNode(long nodeId) throws SQLException {
		m_pstmtGetAllOfNode.setLong(1, nodeId);
		return ResultSetStream.create(m_pstmtGetAllOfNode, m_mapper);
	}

	long create(long nodeId, Geometry loc) throws SQLException {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = m_conn.prepareStatement("INSERT INTO LOCATIONS VALUES (default, ?, default)");
			pstmt.setObject(1, loc);
			pstmt.executeUpdate();
			long id = JdbcUtils.getGeneratedKey(pstmt);

			pstmt = m_conn.prepareStatement("INSERT INTO NODE_TO_LOCATION VALUES (?, ?)");
			pstmt.setLong(1, nodeId);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();
			
			return id;
		}
		finally {
			JdbcUtils.closeQuietly(pstmt);
		}
	}
	
	void delete(long locId) throws SQLException {
		Statement stmt = null;
		try {
			stmt = m_conn.createStatement();
			stmt.executeUpdate("DELETE FROM NODE_TO_LOCATION WHERE loc=" + locId);
			stmt.executeUpdate("DELETE FROM LOCATIONS WHERE id=" + locId);
		}
		finally {
			JdbcUtils.closeQuietly(stmt);
		}
	}
	
	void deleteObsoleteAll() throws SQLException {
		Statement stmt = null;
		try {
			stmt = m_conn.createStatement();
			stmt.executeUpdate("DELETE FROM LOCATIONS "
							+ "WHERE id NOT IN (SELECT loc FROM NODE_TO_LOCATION)");
		}
		finally {
			JdbcUtils.closeQuietly(stmt);
		}
	}
	
	void update(long id, Map<String, Object> udata) throws SQLException {
		JdbcUtils.updateTable(m_conn, "LOCATIONS", id, udata);
	}
	
	private Function<ResultSet,SensorNodeLocationImpl> m_mapper = new Function<ResultSet,SensorNodeLocationImpl>() {
		@Override
		public SensorNodeLocationImpl apply(ResultSet rs) {
			try {
				return new SensorNodeLocationImpl(m_platform, rs.getLong(1),
													(Geometry)rs.getObject(2),
													rs.getTimestamp(3).toInstant());
			}
			catch ( SQLException e ) {
				return null;
			}
		}
	};
}
