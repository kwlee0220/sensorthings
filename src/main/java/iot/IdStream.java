package iot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class IdStream {
	public static Stream<Long> create(PreparedStatement pstmt) throws SQLException {
		return StreamSupport.stream(new IdSpliterator(pstmt.executeQuery()), false);
	}
	
	static class IdSpliterator extends Spliterators.AbstractSpliterator<Long> {
		private final ResultSet m_rs;
		
		protected IdSpliterator(ResultSet rs) {
			super(Long.MAX_VALUE, 0);
			
			m_rs = rs;
		}

		@Override
		public boolean tryAdvance(Consumer<? super Long> action) {
			try {
				if ( m_rs.next() ) {
					action.accept(m_rs.getLong(1));
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
