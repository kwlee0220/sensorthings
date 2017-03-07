 package iot.restful.optor;

import iot.restful.STTuple;

import java.util.List;
import java.util.stream.Stream;

import jersey.repackaged.com.google.common.collect.Iterables;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class Navigate implements ValueStreamOperator {
	private final List<String> m_path;
	
	public Navigate(List<String> path) {
		m_path = path;
	}

	@Override
	public Stream<Object> apply(STTuple tuple) {
		return tuple.navigate(m_path).map(t -> Iterables.getFirst(t.getAttributeAll(), null));
//		Stream<STTuple> tstrm = tuple.navigate(m_path);
//		List<STTuple> tuples = tstrm.collect(Collectors.toList());
//		
//		return Stream.empty();
	}
}
