 package iot.restful.optor;

import iot.restful.STTuple;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class Exists implements BooleanEvaluator {
	private final BooleanStreamOperator m_input;
	
	public Exists(BooleanStreamOperator input) {
		m_input = input;
	}

	@Override
	public boolean eval(STTuple tuple) {
		return m_input.apply(tuple).anyMatch(Boolean::booleanValue);
	}
}
