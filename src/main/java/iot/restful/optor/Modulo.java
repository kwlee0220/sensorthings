 package iot.restful.optor;

import iot.restful.STTuple;

import java.util.stream.Stream;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class Modulo implements ValueStreamOperator {
	private final ValueStreamOperator m_optor;
	private final long m_data;
	
	public Modulo(ValueStreamOperator optor, long data) {
		m_optor = optor;
		m_data = data;
	}

	@Override
	public Stream<Object> apply(STTuple tuple) {
		return m_optor.apply(tuple).map(v -> Utilities.toLong(v) % m_data);
	}
}
