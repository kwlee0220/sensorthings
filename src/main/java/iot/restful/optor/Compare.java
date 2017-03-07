 package iot.restful.optor;

import iot.restful.STTuple;
import iot.restful.parser.FilterExprParser;

import java.util.stream.Stream;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class Compare implements BooleanStreamOperator {
	private final ValueStreamOperator m_input;
	private final int m_cmptor;
	private final Comparable<Object> m_data;
	
	public Compare(ValueStreamOperator input, int cmptor, Comparable<Object> data) {
		m_input = input;
		m_cmptor = cmptor;
		m_data = data;
	}

	@Override
	public Stream<Boolean> apply(STTuple tuple) {
		Stream<Object> vstrm = m_input.apply(tuple);
		return vstrm.map(v -> {
			int cmp = -m_data.compareTo(v);
			switch ( m_cmptor ) {
				case FilterExprParser.EQ:
					return cmp == 0;
				case FilterExprParser.NE:
					return cmp != 0;
				case FilterExprParser.GT:
					return cmp > 0;
				case FilterExprParser.GE:
					return cmp >= 0;
				case FilterExprParser.LT:
					return cmp < 0;
				case FilterExprParser.LE:
					return cmp <= 0;
			}
			return false;
		});
	}
}
