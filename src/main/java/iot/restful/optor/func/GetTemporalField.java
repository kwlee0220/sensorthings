package iot.restful.optor.func;

import iot.restful.parser.FilterExprParser;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Function;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class GetTemporalField implements Function<Object, Object> {
	private final int m_code;
	
	public GetTemporalField(int code) {
		m_code = code;
	}
	
	@Override
	public Object apply(Object data) {
		if ( !(data instanceof Instant) ) {
			throw new RuntimeException("Function 'year' cannot be applied on nontimestamp data");
		}
		
		LocalDateTime ldt = LocalDateTime.ofInstant((Instant)data, ZoneId.systemDefault());
		switch ( m_code ) {
			case FilterExprParser.YEAR:
				return ldt.getYear();
			case FilterExprParser.MONTH:
				return ldt.getMonth();
			case FilterExprParser.DAY:
				return ldt.getDayOfYear();
			case FilterExprParser.HOUR:
				return ldt.getHour();
			case FilterExprParser.MINUTE:
				return ldt.getMinute();
			case FilterExprParser.SECOND:
				return ldt.getSecond();
			case FilterExprParser.DATE:
				return ldt.toLocalDate();
			case FilterExprParser.TIME:
				return ldt.toLocalTime();
		}
		
		throw new RuntimeException("unknown temporal function code " + m_code);
	}
}
