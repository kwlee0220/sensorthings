package iot.restful.optor.func;

import java.util.function.Function;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class StartsWith implements Function<Object, Boolean> {
	private final String m_prefix;
	private final boolean m_reverse;
	
	public StartsWith(String prefix, boolean reverse) {
		m_prefix = prefix;
		m_reverse = reverse;
	}
	
	@Override
	public Boolean apply(Object data) {
		return (m_reverse) ? m_prefix.startsWith(data.toString())
							: data.toString().startsWith(m_prefix);
	}
}
