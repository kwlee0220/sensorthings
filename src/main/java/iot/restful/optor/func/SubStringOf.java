package iot.restful.optor.func;

import java.util.function.Function;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class SubStringOf implements Function<Object, Boolean> {
	private final String m_literal;
	private final boolean m_reverse;
	
	public SubStringOf(String literal) {
		m_literal = literal;
		m_reverse = false;
	}
	
	public SubStringOf(String literal, boolean reverse) {
		m_literal = literal;
		m_reverse = reverse;
	}
	
	@Override
	public Boolean apply(Object data) {
		return (m_reverse) ? data.toString().contains(m_literal)
							: m_literal.contains(data.toString());
	}
}
