package iot.restful.optor.func;

import java.util.function.Function;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class Concat implements Function<Object, Object> {
	private final String m_literal;
	private final boolean m_reverse;
	
	public Concat(String literal, boolean reverse) {
		m_literal = literal;
		m_reverse = reverse;
	}
	
	@Override
	public Object apply(Object data) {
		return (m_reverse) ? m_literal.concat(data.toString())
							: data.toString().concat(m_literal);
	}
}
