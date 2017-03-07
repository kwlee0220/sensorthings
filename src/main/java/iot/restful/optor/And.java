 package iot.restful.optor;

import iot.restful.STTuple;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class And implements BooleanEvaluator {
	private final BooleanEvaluator m_left;
	private final BooleanEvaluator m_right;
	
	public And(BooleanEvaluator left, BooleanEvaluator right) {
		m_left = left;
		m_right = right;
	}

	@Override
	public boolean eval(STTuple tuple) {
		return m_left.eval(tuple) && m_right.eval(tuple);
	}
}
