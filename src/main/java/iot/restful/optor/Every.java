 package iot.restful.optor;

import iot.restful.STTuple;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class Every implements BooleanEvaluator {
	private final BooleanStreamOperator m_input;
	
	public Every(BooleanStreamOperator input) {
		m_input = input;
	}

	@Override
	public boolean eval(STTuple tuple) {
		return m_input.apply(tuple).allMatch(Boolean::booleanValue);
	}
}
