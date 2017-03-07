package iot.restful.optor.func;

import java.util.function.Function;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class SubString implements Function<Object, Object> {
	private final int m_index;
	
	public SubString(long index) {
		m_index = (int)index;
	}
	
	@Override
	public Object apply(Object data) {
		return data.toString().substring(m_index);
	}
}
