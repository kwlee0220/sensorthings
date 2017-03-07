package iot.restful.optor.func;

import java.util.function.Function;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class EndsWith implements Function<Object, Boolean> {
	private final String m_suffix;
	private final boolean m_reverse;
	
	public EndsWith(String suffix, boolean reverse) {
		m_suffix = suffix;
		m_reverse = reverse;
	}
	
	@Override
	public Boolean apply(Object data) {
		return (m_reverse) ? m_suffix.endsWith(data.toString())
							: data.toString().endsWith(m_suffix);
	}
}
