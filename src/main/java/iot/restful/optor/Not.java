 package iot.restful.optor;

import iot.restful.STTuple;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class Not implements BooleanEvaluator {
	private final BooleanEvaluator m_evaltor;
	
	public Not(BooleanEvaluator left) {
		m_evaltor = left;
	}

	@Override
	public boolean eval(STTuple tuple) {
		return !m_evaltor.eval(tuple);
	}
}
