package iot.restful.optor.func;

import java.util.function.Function;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class ToUpper implements Function<Object, Object> {
	@Override
	public Object apply(Object data) {
		return data.toString().toUpperCase();
	}
}
