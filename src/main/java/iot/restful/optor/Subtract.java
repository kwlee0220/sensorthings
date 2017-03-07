 package iot.restful.optor;

import iot.restful.STTuple;

import java.util.stream.Stream;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class Subtract implements ValueStreamOperator {
	private final ValueStreamOperator m_optor;
	private final double m_data;
	
	public Subtract(ValueStreamOperator optor, Object data) {
		m_optor = optor;
		m_data = Utilities.toDouble(data);
	}

	@Override
	public Stream<Object> apply(STTuple tuple) {
		return m_optor.apply(tuple).map(v -> Utilities.toDouble(v) - m_data);
	}
}
