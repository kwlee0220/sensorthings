package iot.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class ResultSetStream {
	public static <T> Stream<T> create(PreparedStatement pstmt, Function<ResultSet,T> mappingFunc)
		throws SQLException {
		return StreamSupport.stream(new RSSpliterator<T>(pstmt.executeQuery(), mappingFunc), false);
	}
	
	public static <T> Stream<T> create(Connection conn, String sql, Function<ResultSet,T> mappingFunc)
		throws SQLException {
		Statement stmt = conn.createStatement();
		try {
			return StreamSupport.stream(new RSSpliterator<T>(stmt.executeQuery(sql), mappingFunc), false);
		}
		catch ( Exception e ) {
			stmt.close();
			
			throw e;
		}
	}
	
	public static <T> T getFirst(PreparedStatement pstmt, Function<ResultSet,T> mappingFunc)
		throws SQLException {
		return create(pstmt, mappingFunc).findFirst().orElse(null);
	}
	
	public static <T> T getFirst(Connection conn, String sql, Function<ResultSet,T> mappingFunc)
		throws SQLException {
		return create(conn, sql, mappingFunc).findFirst().orElse(null);
	}
	
	static class RSSpliterator<T> extends Spliterators.AbstractSpliterator<T> {
		private final ResultSet m_rs;
		private final Function<ResultSet, T> m_mappingFunc;
		
		protected RSSpliterator(ResultSet rs, Function<ResultSet, T> mappingFunc) {
			super(Long.MAX_VALUE, 0);
			
			m_rs = rs;
			m_mappingFunc = mappingFunc;
		}

		@Override
		public boolean tryAdvance(Consumer<? super T> action) {
			try {
				if ( m_rs.next() ) {
					action.accept(m_mappingFunc.apply(m_rs));
					return true;
				}
				else {
					m_rs.close();
					
					return false;
				}
			}
			catch ( SQLException e ) {
				throw new RuntimeException(e);
			}
		}
	}
}
