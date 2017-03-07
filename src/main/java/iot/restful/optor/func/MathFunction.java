package iot.restful.optor.func;

import iot.restful.parser.FilterExprParser;

import java.util.function.Function;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class MathFunction implements Function<Object, Object> {
	private final int m_code;
	
	public MathFunction(int code) {
		m_code = code;
	}
	
	@Override
	public Object apply(Object data) {
		if ( data instanceof Long ) {
			return data;
		}
		if ( data instanceof Double ) {
			double v = ((Double)data).doubleValue();

			switch ( m_code ) {
				case FilterExprParser.ROUND:
					return Math.round(v);
				case FilterExprParser.FLOOR:
					return Math.floor(v);
				case FilterExprParser.CEILING:
					return Math.ceil(v);
				default:
					throw new RuntimeException("unknown temporal function code " + m_code);
			}
		}
		throw new RuntimeException("invalid input data");
	}
}
