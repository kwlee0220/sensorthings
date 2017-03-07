grammar FilterExpr;

@header {
import java.time.*;
import java.util.stream.*;

import iot.restful.*;
import iot.restful.optor.*;
import iot.restful.optor.func.*;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
}

@parser::members {
	private GeometryFactory m_geomFactory;
	
	public FilterExprParser(TokenStream input, GeometryFactory geomFact) {
		this(input);
		
		m_geomFactory = geomFact;
	}
}

import LexerRules;

expr returns [List<String> result]
	: relExpr 
	;
	
relExpr returns [BooleanEvaluator evaltor]
	: NOT b1=boolStreamAggrExpr { $evaltor = new Not($b1.evaltor); }
	| b1=boolStreamAggrExpr AND b2=boolStreamAggrExpr { $evaltor = new And($b1.evaltor, $b2.evaltor); }
	| b1=boolStreamAggrExpr OR b2=boolStreamAggrExpr { $evaltor = new Or($b1.evaltor, $b2.evaltor); }
	| b1=boolStreamAggrExpr { $evaltor = $b1.evaltor; }
	;
	
boolStreamAggrExpr returns [BooleanEvaluator evaltor]
	: EXISTS? boolStreamExpr { $evaltor = new Exists($boolStreamExpr.op); }
	| EVERY boolStreamExpr { $evaltor = new Every($boolStreamExpr.op); }
	;
	
boolStreamExpr returns [BooleanStreamOperator op]
	: source=streamExpr cmp=(EQ|NE|GT|GE|LT|LE) literal
		{ $op = new Compare($source.op, $cmp.getType(), $literal.value); }
	| source=streamExpr
		func=(
			GEO_INTERSECTS
			| ST_EQUALS
			| ST_DISJOINT
			| ST_TOUCHES
			| ST_WITHIN
			| ST_OVERLAPS
			| ST_CROSSES
			| ST_INTERSECTS
			| ST_CONTAINS) '(' geoLiteral ')'
		{ $op = new StBooleanFunction($source.op, $geoLiteral.value, $func.getType()); }
	;
	
streamExpr returns [ValueStreamOperator op]
	: idPath { $op = new Navigate($idPath.path); }
	| source=streamExpr MUL numeric { $op = new Multiply($source.op, $numeric.value); }
	| source=streamExpr DIV numeric { $op = new Divide($source.op, $numeric.value); }
	| source=streamExpr MOD INT { $op = new Modulo($source.op, Long.parseLong($INT.text)); }
	| source=streamExpr ADD numeric { $op = new Add($source.op, $numeric.value); }
	| source=streamExpr SUB numeric { $op = new Subtract($source.op, $numeric.value); }
	| streamExpr SUB numeric { $op = new Subtract($source.op, $numeric.value); }
	| LENGTH '(' source=streamExpr ')' { $op = new Transform($source.op, new Length()); }
	| INDEX_OF '(' source=streamExpr ',' lit=stringExpr ')'
		{ $op = new Transform($source.op, new IndexOf($lit.value, false)); }
	| INDEX_OF '(' lit=stringExpr ',' source=streamExpr ')'
		{ $op = new Transform($source.op, new IndexOf($lit.value, true)); }
	| SUBSTRING '(' source=streamExpr ',' intExpr ')'
		{ $op = new Transform($source.op, new SubString($intExpr.value)); }
	| TOLOWER '(' source=streamExpr ')' { $op = new Transform($source.op, new ToLower()); }
	| TOUPPER '(' source=streamExpr ')' { $op = new Transform($source.op, new ToUpper()); }
	| TRIM '(' source=streamExpr ')' { $op = new Transform($source.op, new Trim()); }
	| CONCAT '(' source=streamExpr ',' lit=stringExpr ')'
		{ $op = new Transform($source.op, new Concat($lit.value, false)); }
	| CONCAT '(' lit=stringExpr ',' source=streamExpr ')'
		{ $op = new Transform($source.op, new Concat($lit.value, true)); }
	| GEO_DISTANCE '(' left=streamExpr ',' right=stringExpr ')'
	| GEO_LENGTH '(' left=streamExpr ')'
	| '(' source=streamExpr ')' { $op = $source.op; }
	| code=( YEAR | MONTH | DAY | HOUR | MINUTE | SECOND | DATE | TIME )
		'(' source=streamExpr ')' { $op = new Transform($source.op, new GetTemporalField($code.getType())); }
	| code=(ROUND | FLOOR | CEILING ) '(' streamExpr ')'
		{ $op = new Transform($source.op, new MathFunction($code.getType())); }
	;

idPath returns [List<String> path]
@init { $path = new ArrayList<String>(); }
	: ID { $path.add($ID.text); }
		( '/' link=ID { $path.add($link.text); } )*
	;
	 
literal returns [Comparable value]
	: numeric { $value = $numeric.value; }
	| stringExpr { $value = $stringExpr.value; }
	| NOW '(' ')' { $value = Instant.now(); }
	;
	
geoLiteral returns [STGeometry value]
	: wktGeometry { $value = new STGeometry($wktGeometry.value); }
	;
	
wktGeometry returns [Geometry value]
	: 'POINT' '(' c=coord ')'
		{ $value = m_geomFactory.createPoint($c.value); }
	| 'LINESTRING' seq=coordSeq
		{ $value = m_geomFactory.createLineString($seq.value); }
	| 'POLYGON' seqList=coordSeqList {
		LinearRing shell = m_geomFactory.createLinearRing($seqList.value.get(0));
		if ( $seqList.value.size() == 1 ) {
			$value = m_geomFactory.createPolygon(shell, null);
		}
		else {
			LinearRing[] holes = $seqList.value.stream()
										.skip(1)
										.map(seq -> m_geomFactory.createLinearRing(seq))
										.toArray(size -> new LinearRing[size]);
			$value = m_geomFactory.createPolygon(shell, holes);
		}
	}
	;
	
coordSeqList returns [List<CoordinateSequence> value]
	: '(' h=coordSeq { $value = new ArrayList<CoordinateSequence>(); $value.add($h.value); } (',' t=coordSeq { $value.add($t.value); } )* ')'
	;

coordSeq returns [CoordinateSequence value]
locals [List<Coordinate> coordsList = new ArrayList<Coordinate>()]
@after {
	Coordinate[] coords = $coordsList.toArray(new Coordinate[$coordsList.size()]);
	$value = m_geomFactory.getCoordinateSequenceFactory().create(coords);
}
	: '(' h=coord { $coordsList.add($h.value); } (',' t=coord { $coordsList.add($t.value); } )* ')'
	;
	
coord returns [Coordinate value]
locals [double zval=0]
	: x=coordSeg y=coordSeg (z=coordSeg { $zval = $z.value; } )?
		{ $value = new Coordinate($x.value, $y.value, $zval); }
	;
	
coordSeg returns [double value]
	: INT { $value = Double.parseDouble($INT.text); }
	| FLOAT { $value = Double.parseDouble($FLOAT.text); }
	;
	
numeric returns [Comparable value]
	: intExpr { $value = $intExpr.value; }
	| FLOAT { $value = Double.parseDouble($FLOAT.text); }
	;
	
intExpr returns [long value]
	: INT { $value = Long.parseLong($INT.text); }
	;
	
stringExpr returns [String value]
	: STRING { $value = $STRING.text.substring(1, $STRING.text.length()-1); }
	;

EVERY: 'every' ;
EXISTS: 'exists' ;

ADD: 'add' ;
SUB: 'sub' ;
MUL: 'mul' ;
DIV: 'div' ;
MOD: 'mod' ;

EQ: 'eq' ;
NE: 'ne' ;
GT: 'gt' ;
GE: 'ge' ;
LT: 'lt' ;
LE: 'le' ;

AND: 'and' ;
OR: 'or' ;
NOT: 'not' ;

SUBSTRING_OF: 'substringof';
ENDSWITH: 'endswith';
STARTSWITH: 'startswith';
LENGTH: 'length';
INDEX_OF: 'indexof';
SUBSTRING: 'substring';
TOLOWER: 'tolower';
TOUPPER: 'toupper';
TRIM: 'trim';
CONCAT: 'concat';

NOW				: 'now' ;
YEAR			: 'year' ;
MONTH			: 'month' ;
DAY				: 'day' ;
HOUR			: 'hour' ;
MINUTE			: 'minute' ;
SECOND			: 'second' ;
FRACTIONALSECONDS	: 'fractionalseconds' ;
DATE			: 'date' ;
TIME			: 'time' ;
TOTALOFFSETMINUTES	: 'totaloffsetiminutes' ;
MINDATETIME		: 'mindatetime' ;
MAXDATETIME		: 'maxdatetime' ;

ROUND			: 'round' ;
FLOOR			: 'floor' ;
CEILING			: 'ceiling' ;

CAST			: 'cast' ;
ISOF			: 'isof' ;

GEO_DISTANCE	: 'geo.distance';
GEO_LENGTH		: 'geo.length';
GEO_INTERSECTS	: 'geo.intersects';
ST_EQUALS		: 'st_equals';
ST_DISJOINT		: 'st_disjoint';
ST_TOUCHES		: 'st_touches';
ST_WITHIN		: 'st_within';
ST_OVERLAPS		: 'st_overlaps';
ST_CROSSES		: 'st_crosses';
ST_INTERSECTS	: 'st_intersects';
ST_CONTAINS		: 'st_contains';
ST_RELATE		: 'st_relate';