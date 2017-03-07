 package iot.restful.optor.func;

import iot.restful.STTuple;
import iot.restful.optor.BooleanStreamOperator;
import iot.restful.optor.ValueStreamOperator;

import java.util.function.Function;
import java.util.stream.Stream;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class BooleanClassifier implements BooleanStreamOperator {
	private final ValueStreamOperator m_optor;
	private final Function<Object,Boolean> m_classifier;
	
	public BooleanClassifier(ValueStreamOperator input, Function<Object,Boolean> classifier) {
		m_optor = input;
		m_classifier = classifier;
	}

	@Override
	public Stream<Boolean> apply(STTuple tuple) {
		return m_optor.apply(tuple).map(m_classifier);
	}
}
