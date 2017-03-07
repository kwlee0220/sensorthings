 package iot.restful.optor.func;

import iot.restful.STGeometry;
import iot.restful.STTuple;
import iot.restful.optor.BooleanStreamOperator;
import iot.restful.optor.ValueStreamOperator;
import iot.restful.parser.FilterExprParser;

import java.util.stream.Stream;

import com.vividsolutions.jts.geom.Geometry;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class StBooleanFunction implements BooleanStreamOperator {
	private final ValueStreamOperator m_optor;
	private final Geometry m_literal;
	private final int m_type;
	
	public StBooleanFunction(ValueStreamOperator input, STGeometry literal, int type) {
		m_optor = input;
		m_literal = literal.getGeometry();
		m_type = type;
	}

	@Override
	public Stream<Boolean> apply(STTuple tuple) {
		return m_optor.apply(tuple).map(v -> {
			if ( !(v instanceof STGeometry) ) {
				throw new RuntimeException("invalid data for Geospatial function");
			}
			Geometry geom = ((STGeometry)v).getGeometry();
			
			if ( geom != null ) {
				switch ( m_type ) {
					case FilterExprParser.GEO_INTERSECTS:
						return geom.intersects(m_literal);
					case FilterExprParser.ST_EQUALS:
						return geom.equals(m_literal);
					case FilterExprParser.ST_DISJOINT:
						return geom.disjoint(m_literal);
					case FilterExprParser.ST_TOUCHES:
						return geom.touches(m_literal);
					case FilterExprParser.ST_WITHIN:
						return geom.within(m_literal);
					case FilterExprParser.ST_OVERLAPS:
						return geom.overlaps(m_literal);
					case FilterExprParser.ST_CROSSES:
						return geom.crosses(m_literal);
					case FilterExprParser.ST_INTERSECTS:
						return geom.intersects(m_literal);
					case FilterExprParser.ST_CONTAINS:
						return geom.contains(m_literal);
				}
			}
			
			return false;
		});
	}
}
