 package iot.restful.optor.func;

import iot.restful.STTuple;
import iot.restful.optor.ValueStreamOperator;

import java.util.function.Function;
import java.util.stream.Stream;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class Transform implements ValueStreamOperator {
	private final ValueStreamOperator m_optor;
	private final Function<Object,Object> m_func;
	
	public Transform(ValueStreamOperator source, Function<Object,Object> func) {
		m_optor = source;
		m_func = func;
	}

	@Override
	public Stream<Object> apply(STTuple tuple) {
		return m_optor.apply(tuple).map(m_func);
	}
}
