package iot.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import jersey.repackaged.com.google.common.collect.Lists;

import com.vividsolutions.jts.geom.Geometry;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class JdbcUtils {
	public static void closeQuietly(Object... jdbcObjs) {
		for ( Object o: jdbcObjs ) {
			try {
				if ( o instanceof Statement ) {
					((Statement)o).close();
				}
				else if ( o instanceof ResultSet ) {
					((ResultSet)o).close();
				}
				else if ( o instanceof Connection ) {
					((Connection)o).close();
				}
			}
			catch ( SQLException ignored ) {
			}
		}
	}
	
	public static long getGeneratedKey(PreparedStatement pstmt) {
		ResultSet keys = null;
		try {
			keys = pstmt.getGeneratedKeys();
			if ( keys.next() ) {
				return keys.getLong(1);
			}
			
			return -1;
		}
		catch ( SQLException e ) {
			throw new RuntimeException(e);
		}
		finally {
			if ( keys != null ) {
				try {
					keys.close();
				}
				catch ( SQLException ignored ) { }
			}
		}
	}
	
	static void updateTable(Connection conn, String tblName, long id, Map<String,Object> data)
		throws SQLException {
		PreparedStatement pstmt = null;
		try {
			StringBuilder builder = new StringBuilder("UPDATE ").append(tblName).append(" SET ");
			for ( String colName: data.keySet() ) {
				builder.append(colName + "=?, ");
			}
			builder.setLength(builder.length()-2);
			builder.append(" WHERE id=?");
			
			pstmt = conn.prepareStatement(builder.toString());
			JdbcUtils.setParameters(pstmt, data);
			pstmt.setLong(data.size()+1, id);
			pstmt.executeUpdate();
		}
		finally {
			JdbcUtils.closeQuietly(pstmt);
		}
	}
	
	static void setParameters(PreparedStatement pstmt, Map<String,Object> data) throws SQLException {
		int idx = 1;
		for ( Object col: data.values() ) {
			if ( col instanceof String ) {
				pstmt.setString(idx, (String)col);
			}
			else if ( col instanceof Integer ) {
				pstmt.setInt(idx, (Integer)col);
			}
			else if ( col instanceof Geometry ) {
				pstmt.setObject(idx, (Geometry)col);
			}
			else if ( col instanceof Float ) {
				pstmt.setFloat(idx, (Float)col);
			}
			
			++idx;
		}
	}
	
	public static <T> List<T> toList(ResultSet rs, Function<ResultSet,T> mapper) throws SQLException {
		List<T> coll = Lists.newArrayList();
		
		while ( rs.next() ) {
			coll.add(mapper.apply(rs));
		}
		
		return coll;
	}
}
