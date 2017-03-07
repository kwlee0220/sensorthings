package iot.restful;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class Expander implements Function<STTuple, STTuple> {
	private final String m_field;
	
	public Expander(String field) {
		m_field = field;
	}

	@Override
	public STTuple apply(STTuple tuple) {
		List<Map<String,Object>> components = tuple.navigate(m_field)
									.map(t -> t.serialize())
									.collect(Collectors.toList());
		tuple = tuple.duplicate().setAttribute(m_field, components);
		return tuple;
	}

}
