package iot.restful.optor;

import iot.restful.STTuple;

import java.util.function.Function;
import java.util.stream.Stream;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public interface BooleanStreamOperator extends Function<STTuple, Stream<Boolean>> {
}
