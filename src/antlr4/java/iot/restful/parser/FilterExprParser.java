// Generated from I:\frc2011_java\camus\misc\iot_platform/src/main/resources/FilterExpr.g4 by ANTLR 4.5
package iot.restful.parser;

import java.time.*;
import java.util.stream.*;

import iot.restful.*;
import iot.restful.optor.*;
import iot.restful.optor.func.*;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FilterExprParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, EVERY=8, EXISTS=9, 
		ADD=10, SUB=11, MUL=12, DIV=13, MOD=14, EQ=15, NE=16, GT=17, GE=18, LT=19, 
		LE=20, AND=21, OR=22, NOT=23, SUBSTRING_OF=24, ENDSWITH=25, STARTSWITH=26, 
		LENGTH=27, INDEX_OF=28, SUBSTRING=29, TOLOWER=30, TOUPPER=31, TRIM=32, 
		CONCAT=33, NOW=34, YEAR=35, MONTH=36, DAY=37, HOUR=38, MINUTE=39, SECOND=40, 
		FRACTIONALSECONDS=41, DATE=42, TIME=43, TOTALOFFSETMINUTES=44, MINDATETIME=45, 
		MAXDATETIME=46, ROUND=47, FLOOR=48, CEILING=49, CAST=50, ISOF=51, GEO_DISTANCE=52, 
		GEO_LENGTH=53, GEO_INTERSECTS=54, ST_EQUALS=55, ST_DISJOINT=56, ST_TOUCHES=57, 
		ST_WITHIN=58, ST_OVERLAPS=59, ST_CROSSES=60, ST_INTERSECTS=61, ST_CONTAINS=62, 
		ST_RELATE=63, ID=64, INT=65, FLOAT=66, STRING=67, LINE_COMMENT=68, COMMENT=69, 
		WS=70;
	public static final int
		RULE_expr = 0, RULE_relExpr = 1, RULE_boolStreamAggrExpr = 2, RULE_boolStreamExpr = 3, 
		RULE_streamExpr = 4, RULE_idPath = 5, RULE_literal = 6, RULE_geoLiteral = 7, 
		RULE_wktGeometry = 8, RULE_coordSeqList = 9, RULE_coordSeq = 10, RULE_coord = 11, 
		RULE_coordSeg = 12, RULE_numeric = 13, RULE_intExpr = 14, RULE_stringExpr = 15;
	public static final String[] ruleNames = {
		"expr", "relExpr", "boolStreamAggrExpr", "boolStreamExpr", "streamExpr", 
		"idPath", "literal", "geoLiteral", "wktGeometry", "coordSeqList", "coordSeq", 
		"coord", "coordSeg", "numeric", "intExpr", "stringExpr"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "','", "'/'", "'POINT'", "'LINESTRING'", "'POLYGON'", 
		"'every'", "'exists'", "'add'", "'sub'", "'mul'", "'div'", "'mod'", "'eq'", 
		"'ne'", "'gt'", "'ge'", "'lt'", "'le'", "'and'", "'or'", "'not'", "'substringof'", 
		"'endswith'", "'startswith'", "'length'", "'indexof'", "'substring'", 
		"'tolower'", "'toupper'", "'trim'", "'concat'", "'now'", "'year'", "'month'", 
		"'day'", "'hour'", "'minute'", "'second'", "'fractionalseconds'", "'date'", 
		"'time'", "'totaloffsetiminutes'", "'mindatetime'", "'maxdatetime'", "'round'", 
		"'floor'", "'ceiling'", "'cast'", "'isof'", "'geo.distance'", "'geo.length'", 
		"'geo.intersects'", "'st_equals'", "'st_disjoint'", "'st_touches'", "'st_within'", 
		"'st_overlaps'", "'st_crosses'", "'st_intersects'", "'st_contains'", "'st_relate'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, "EVERY", "EXISTS", "ADD", 
		"SUB", "MUL", "DIV", "MOD", "EQ", "NE", "GT", "GE", "LT", "LE", "AND", 
		"OR", "NOT", "SUBSTRING_OF", "ENDSWITH", "STARTSWITH", "LENGTH", "INDEX_OF", 
		"SUBSTRING", "TOLOWER", "TOUPPER", "TRIM", "CONCAT", "NOW", "YEAR", "MONTH", 
		"DAY", "HOUR", "MINUTE", "SECOND", "FRACTIONALSECONDS", "DATE", "TIME", 
		"TOTALOFFSETMINUTES", "MINDATETIME", "MAXDATETIME", "ROUND", "FLOOR", 
		"CEILING", "CAST", "ISOF", "GEO_DISTANCE", "GEO_LENGTH", "GEO_INTERSECTS", 
		"ST_EQUALS", "ST_DISJOINT", "ST_TOUCHES", "ST_WITHIN", "ST_OVERLAPS", 
		"ST_CROSSES", "ST_INTERSECTS", "ST_CONTAINS", "ST_RELATE", "ID", "INT", 
		"FLOAT", "STRING", "LINE_COMMENT", "COMMENT", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "FilterExpr.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


		private GeometryFactory m_geomFactory;
		
		public FilterExprParser(TokenStream input, GeometryFactory geomFact) {
			this(input);
			
			m_geomFactory = geomFact;
		}

	public FilterExprParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ExprContext extends ParserRuleContext {
		public List<String> result;
		public RelExprContext relExpr() {
			return getRuleContext(RelExprContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			relExpr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelExprContext extends ParserRuleContext {
		public BooleanEvaluator evaltor;
		public BoolStreamAggrExprContext b1;
		public BoolStreamAggrExprContext b2;
		public TerminalNode NOT() { return getToken(FilterExprParser.NOT, 0); }
		public List<BoolStreamAggrExprContext> boolStreamAggrExpr() {
			return getRuleContexts(BoolStreamAggrExprContext.class);
		}
		public BoolStreamAggrExprContext boolStreamAggrExpr(int i) {
			return getRuleContext(BoolStreamAggrExprContext.class,i);
		}
		public TerminalNode AND() { return getToken(FilterExprParser.AND, 0); }
		public TerminalNode OR() { return getToken(FilterExprParser.OR, 0); }
		public RelExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relExpr; }
	}

	public final RelExprContext relExpr() throws RecognitionException {
		RelExprContext _localctx = new RelExprContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_relExpr);
		try {
			setState(51);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(34);
				match(NOT);
				setState(35);
				((RelExprContext)_localctx).b1 = boolStreamAggrExpr();
				 ((RelExprContext)_localctx).evaltor =  new Not(((RelExprContext)_localctx).b1.evaltor); 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(38);
				((RelExprContext)_localctx).b1 = boolStreamAggrExpr();
				setState(39);
				match(AND);
				setState(40);
				((RelExprContext)_localctx).b2 = boolStreamAggrExpr();
				 ((RelExprContext)_localctx).evaltor =  new And(((RelExprContext)_localctx).b1.evaltor, ((RelExprContext)_localctx).b2.evaltor); 
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(43);
				((RelExprContext)_localctx).b1 = boolStreamAggrExpr();
				setState(44);
				match(OR);
				setState(45);
				((RelExprContext)_localctx).b2 = boolStreamAggrExpr();
				 ((RelExprContext)_localctx).evaltor =  new Or(((RelExprContext)_localctx).b1.evaltor, ((RelExprContext)_localctx).b2.evaltor); 
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(48);
				((RelExprContext)_localctx).b1 = boolStreamAggrExpr();
				 ((RelExprContext)_localctx).evaltor =  ((RelExprContext)_localctx).b1.evaltor; 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoolStreamAggrExprContext extends ParserRuleContext {
		public BooleanEvaluator evaltor;
		public BoolStreamExprContext boolStreamExpr;
		public BoolStreamExprContext boolStreamExpr() {
			return getRuleContext(BoolStreamExprContext.class,0);
		}
		public TerminalNode EXISTS() { return getToken(FilterExprParser.EXISTS, 0); }
		public TerminalNode EVERY() { return getToken(FilterExprParser.EVERY, 0); }
		public BoolStreamAggrExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolStreamAggrExpr; }
	}

	public final BoolStreamAggrExprContext boolStreamAggrExpr() throws RecognitionException {
		BoolStreamAggrExprContext _localctx = new BoolStreamAggrExprContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_boolStreamAggrExpr);
		int _la;
		try {
			setState(63);
			switch (_input.LA(1)) {
			case T__0:
			case EXISTS:
			case LENGTH:
			case INDEX_OF:
			case SUBSTRING:
			case TOLOWER:
			case TOUPPER:
			case TRIM:
			case CONCAT:
			case YEAR:
			case MONTH:
			case DAY:
			case HOUR:
			case MINUTE:
			case SECOND:
			case DATE:
			case TIME:
			case ROUND:
			case FLOOR:
			case CEILING:
			case GEO_DISTANCE:
			case GEO_LENGTH:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(54);
				_la = _input.LA(1);
				if (_la==EXISTS) {
					{
					setState(53);
					match(EXISTS);
					}
				}

				setState(56);
				((BoolStreamAggrExprContext)_localctx).boolStreamExpr = boolStreamExpr();
				 ((BoolStreamAggrExprContext)_localctx).evaltor =  new Exists(((BoolStreamAggrExprContext)_localctx).boolStreamExpr.op); 
				}
				break;
			case EVERY:
				enterOuterAlt(_localctx, 2);
				{
				setState(59);
				match(EVERY);
				setState(60);
				((BoolStreamAggrExprContext)_localctx).boolStreamExpr = boolStreamExpr();
				 ((BoolStreamAggrExprContext)_localctx).evaltor =  new Every(((BoolStreamAggrExprContext)_localctx).boolStreamExpr.op); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoolStreamExprContext extends ParserRuleContext {
		public BooleanStreamOperator op;
		public StreamExprContext source;
		public Token cmp;
		public LiteralContext literal;
		public Token func;
		public GeoLiteralContext geoLiteral;
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public StreamExprContext streamExpr() {
			return getRuleContext(StreamExprContext.class,0);
		}
		public TerminalNode EQ() { return getToken(FilterExprParser.EQ, 0); }
		public TerminalNode NE() { return getToken(FilterExprParser.NE, 0); }
		public TerminalNode GT() { return getToken(FilterExprParser.GT, 0); }
		public TerminalNode GE() { return getToken(FilterExprParser.GE, 0); }
		public TerminalNode LT() { return getToken(FilterExprParser.LT, 0); }
		public TerminalNode LE() { return getToken(FilterExprParser.LE, 0); }
		public GeoLiteralContext geoLiteral() {
			return getRuleContext(GeoLiteralContext.class,0);
		}
		public TerminalNode GEO_INTERSECTS() { return getToken(FilterExprParser.GEO_INTERSECTS, 0); }
		public TerminalNode ST_EQUALS() { return getToken(FilterExprParser.ST_EQUALS, 0); }
		public TerminalNode ST_DISJOINT() { return getToken(FilterExprParser.ST_DISJOINT, 0); }
		public TerminalNode ST_TOUCHES() { return getToken(FilterExprParser.ST_TOUCHES, 0); }
		public TerminalNode ST_WITHIN() { return getToken(FilterExprParser.ST_WITHIN, 0); }
		public TerminalNode ST_OVERLAPS() { return getToken(FilterExprParser.ST_OVERLAPS, 0); }
		public TerminalNode ST_CROSSES() { return getToken(FilterExprParser.ST_CROSSES, 0); }
		public TerminalNode ST_INTERSECTS() { return getToken(FilterExprParser.ST_INTERSECTS, 0); }
		public TerminalNode ST_CONTAINS() { return getToken(FilterExprParser.ST_CONTAINS, 0); }
		public BoolStreamExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolStreamExpr; }
	}

	public final BoolStreamExprContext boolStreamExpr() throws RecognitionException {
		BoolStreamExprContext _localctx = new BoolStreamExprContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_boolStreamExpr);
		int _la;
		try {
			setState(77);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(65);
				((BoolStreamExprContext)_localctx).source = streamExpr(0);
				setState(66);
				((BoolStreamExprContext)_localctx).cmp = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQ) | (1L << NE) | (1L << GT) | (1L << GE) | (1L << LT) | (1L << LE))) != 0)) ) {
					((BoolStreamExprContext)_localctx).cmp = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(67);
				((BoolStreamExprContext)_localctx).literal = literal();
				 ((BoolStreamExprContext)_localctx).op =  new Compare(((BoolStreamExprContext)_localctx).source.op, ((BoolStreamExprContext)_localctx).cmp.getType(), ((BoolStreamExprContext)_localctx).literal.value); 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(70);
				((BoolStreamExprContext)_localctx).source = streamExpr(0);
				setState(71);
				((BoolStreamExprContext)_localctx).func = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GEO_INTERSECTS) | (1L << ST_EQUALS) | (1L << ST_DISJOINT) | (1L << ST_TOUCHES) | (1L << ST_WITHIN) | (1L << ST_OVERLAPS) | (1L << ST_CROSSES) | (1L << ST_INTERSECTS) | (1L << ST_CONTAINS))) != 0)) ) {
					((BoolStreamExprContext)_localctx).func = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(72);
				match(T__0);
				setState(73);
				((BoolStreamExprContext)_localctx).geoLiteral = geoLiteral();
				setState(74);
				match(T__1);
				 ((BoolStreamExprContext)_localctx).op =  new StBooleanFunction(((BoolStreamExprContext)_localctx).source.op, ((BoolStreamExprContext)_localctx).geoLiteral.value, ((BoolStreamExprContext)_localctx).func.getType()); 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StreamExprContext extends ParserRuleContext {
		public ValueStreamOperator op;
		public StreamExprContext source;
		public IdPathContext idPath;
		public StringExprContext lit;
		public IntExprContext intExpr;
		public StreamExprContext left;
		public StringExprContext right;
		public Token code;
		public NumericContext numeric;
		public Token INT;
		public IdPathContext idPath() {
			return getRuleContext(IdPathContext.class,0);
		}
		public TerminalNode LENGTH() { return getToken(FilterExprParser.LENGTH, 0); }
		public StreamExprContext streamExpr() {
			return getRuleContext(StreamExprContext.class,0);
		}
		public TerminalNode INDEX_OF() { return getToken(FilterExprParser.INDEX_OF, 0); }
		public StringExprContext stringExpr() {
			return getRuleContext(StringExprContext.class,0);
		}
		public TerminalNode SUBSTRING() { return getToken(FilterExprParser.SUBSTRING, 0); }
		public IntExprContext intExpr() {
			return getRuleContext(IntExprContext.class,0);
		}
		public TerminalNode TOLOWER() { return getToken(FilterExprParser.TOLOWER, 0); }
		public TerminalNode TOUPPER() { return getToken(FilterExprParser.TOUPPER, 0); }
		public TerminalNode TRIM() { return getToken(FilterExprParser.TRIM, 0); }
		public TerminalNode CONCAT() { return getToken(FilterExprParser.CONCAT, 0); }
		public TerminalNode GEO_DISTANCE() { return getToken(FilterExprParser.GEO_DISTANCE, 0); }
		public TerminalNode GEO_LENGTH() { return getToken(FilterExprParser.GEO_LENGTH, 0); }
		public TerminalNode YEAR() { return getToken(FilterExprParser.YEAR, 0); }
		public TerminalNode MONTH() { return getToken(FilterExprParser.MONTH, 0); }
		public TerminalNode DAY() { return getToken(FilterExprParser.DAY, 0); }
		public TerminalNode HOUR() { return getToken(FilterExprParser.HOUR, 0); }
		public TerminalNode MINUTE() { return getToken(FilterExprParser.MINUTE, 0); }
		public TerminalNode SECOND() { return getToken(FilterExprParser.SECOND, 0); }
		public TerminalNode DATE() { return getToken(FilterExprParser.DATE, 0); }
		public TerminalNode TIME() { return getToken(FilterExprParser.TIME, 0); }
		public TerminalNode ROUND() { return getToken(FilterExprParser.ROUND, 0); }
		public TerminalNode FLOOR() { return getToken(FilterExprParser.FLOOR, 0); }
		public TerminalNode CEILING() { return getToken(FilterExprParser.CEILING, 0); }
		public TerminalNode MUL() { return getToken(FilterExprParser.MUL, 0); }
		public NumericContext numeric() {
			return getRuleContext(NumericContext.class,0);
		}
		public TerminalNode DIV() { return getToken(FilterExprParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(FilterExprParser.MOD, 0); }
		public TerminalNode INT() { return getToken(FilterExprParser.INT, 0); }
		public TerminalNode ADD() { return getToken(FilterExprParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(FilterExprParser.SUB, 0); }
		public StreamExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_streamExpr; }
	}

	public final StreamExprContext streamExpr() throws RecognitionException {
		return streamExpr(0);
	}

	private StreamExprContext streamExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		StreamExprContext _localctx = new StreamExprContext(_ctx, _parentState);
		StreamExprContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_streamExpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(176);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(80);
				((StreamExprContext)_localctx).idPath = idPath();
				 ((StreamExprContext)_localctx).op =  new Navigate(((StreamExprContext)_localctx).idPath.path); 
				}
				break;
			case 2:
				{
				setState(83);
				match(LENGTH);
				setState(84);
				match(T__0);
				setState(85);
				((StreamExprContext)_localctx).source = streamExpr(0);
				setState(86);
				match(T__1);
				 ((StreamExprContext)_localctx).op =  new Transform(((StreamExprContext)_localctx).source.op, new Length()); 
				}
				break;
			case 3:
				{
				setState(89);
				match(INDEX_OF);
				setState(90);
				match(T__0);
				setState(91);
				((StreamExprContext)_localctx).source = streamExpr(0);
				setState(92);
				match(T__2);
				setState(93);
				((StreamExprContext)_localctx).lit = stringExpr();
				setState(94);
				match(T__1);
				 ((StreamExprContext)_localctx).op =  new Transform(((StreamExprContext)_localctx).source.op, new IndexOf(((StreamExprContext)_localctx).lit.value, false)); 
				}
				break;
			case 4:
				{
				setState(97);
				match(INDEX_OF);
				setState(98);
				match(T__0);
				setState(99);
				((StreamExprContext)_localctx).lit = stringExpr();
				setState(100);
				match(T__2);
				setState(101);
				((StreamExprContext)_localctx).source = streamExpr(0);
				setState(102);
				match(T__1);
				 ((StreamExprContext)_localctx).op =  new Transform(((StreamExprContext)_localctx).source.op, new IndexOf(((StreamExprContext)_localctx).lit.value, true)); 
				}
				break;
			case 5:
				{
				setState(105);
				match(SUBSTRING);
				setState(106);
				match(T__0);
				setState(107);
				((StreamExprContext)_localctx).source = streamExpr(0);
				setState(108);
				match(T__2);
				setState(109);
				((StreamExprContext)_localctx).intExpr = intExpr();
				setState(110);
				match(T__1);
				 ((StreamExprContext)_localctx).op =  new Transform(((StreamExprContext)_localctx).source.op, new SubString(((StreamExprContext)_localctx).intExpr.value)); 
				}
				break;
			case 6:
				{
				setState(113);
				match(TOLOWER);
				setState(114);
				match(T__0);
				setState(115);
				((StreamExprContext)_localctx).source = streamExpr(0);
				setState(116);
				match(T__1);
				 ((StreamExprContext)_localctx).op =  new Transform(((StreamExprContext)_localctx).source.op, new ToLower()); 
				}
				break;
			case 7:
				{
				setState(119);
				match(TOUPPER);
				setState(120);
				match(T__0);
				setState(121);
				((StreamExprContext)_localctx).source = streamExpr(0);
				setState(122);
				match(T__1);
				 ((StreamExprContext)_localctx).op =  new Transform(((StreamExprContext)_localctx).source.op, new ToUpper()); 
				}
				break;
			case 8:
				{
				setState(125);
				match(TRIM);
				setState(126);
				match(T__0);
				setState(127);
				((StreamExprContext)_localctx).source = streamExpr(0);
				setState(128);
				match(T__1);
				 ((StreamExprContext)_localctx).op =  new Transform(((StreamExprContext)_localctx).source.op, new Trim()); 
				}
				break;
			case 9:
				{
				setState(131);
				match(CONCAT);
				setState(132);
				match(T__0);
				setState(133);
				((StreamExprContext)_localctx).source = streamExpr(0);
				setState(134);
				match(T__2);
				setState(135);
				((StreamExprContext)_localctx).lit = stringExpr();
				setState(136);
				match(T__1);
				 ((StreamExprContext)_localctx).op =  new Transform(((StreamExprContext)_localctx).source.op, new Concat(((StreamExprContext)_localctx).lit.value, false)); 
				}
				break;
			case 10:
				{
				setState(139);
				match(CONCAT);
				setState(140);
				match(T__0);
				setState(141);
				((StreamExprContext)_localctx).lit = stringExpr();
				setState(142);
				match(T__2);
				setState(143);
				((StreamExprContext)_localctx).source = streamExpr(0);
				setState(144);
				match(T__1);
				 ((StreamExprContext)_localctx).op =  new Transform(((StreamExprContext)_localctx).source.op, new Concat(((StreamExprContext)_localctx).lit.value, true)); 
				}
				break;
			case 11:
				{
				setState(147);
				match(GEO_DISTANCE);
				setState(148);
				match(T__0);
				setState(149);
				((StreamExprContext)_localctx).left = streamExpr(0);
				setState(150);
				match(T__2);
				setState(151);
				((StreamExprContext)_localctx).right = stringExpr();
				setState(152);
				match(T__1);
				}
				break;
			case 12:
				{
				setState(154);
				match(GEO_LENGTH);
				setState(155);
				match(T__0);
				setState(156);
				((StreamExprContext)_localctx).left = streamExpr(0);
				setState(157);
				match(T__1);
				}
				break;
			case 13:
				{
				setState(159);
				match(T__0);
				setState(160);
				((StreamExprContext)_localctx).source = streamExpr(0);
				setState(161);
				match(T__1);
				 ((StreamExprContext)_localctx).op =  ((StreamExprContext)_localctx).source.op; 
				}
				break;
			case 14:
				{
				setState(164);
				((StreamExprContext)_localctx).code = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << YEAR) | (1L << MONTH) | (1L << DAY) | (1L << HOUR) | (1L << MINUTE) | (1L << SECOND) | (1L << DATE) | (1L << TIME))) != 0)) ) {
					((StreamExprContext)_localctx).code = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(165);
				match(T__0);
				setState(166);
				((StreamExprContext)_localctx).source = streamExpr(0);
				setState(167);
				match(T__1);
				 ((StreamExprContext)_localctx).op =  new Transform(((StreamExprContext)_localctx).source.op, new GetTemporalField(((StreamExprContext)_localctx).code.getType())); 
				}
				break;
			case 15:
				{
				setState(170);
				((StreamExprContext)_localctx).code = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ROUND) | (1L << FLOOR) | (1L << CEILING))) != 0)) ) {
					((StreamExprContext)_localctx).code = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(171);
				match(T__0);
				setState(172);
				streamExpr(0);
				setState(173);
				match(T__1);
				 ((StreamExprContext)_localctx).op =  new Transform(((StreamExprContext)_localctx).source.op, new MathFunction(((StreamExprContext)_localctx).code.getType())); 
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(209);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(207);
					switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
					case 1:
						{
						_localctx = new StreamExprContext(_parentctx, _parentState);
						_localctx.source = _prevctx;
						_localctx.source = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_streamExpr);
						setState(178);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(179);
						match(MUL);
						setState(180);
						((StreamExprContext)_localctx).numeric = numeric();
						 ((StreamExprContext)_localctx).op =  new Multiply(((StreamExprContext)_localctx).source.op, ((StreamExprContext)_localctx).numeric.value); 
						}
						break;
					case 2:
						{
						_localctx = new StreamExprContext(_parentctx, _parentState);
						_localctx.source = _prevctx;
						_localctx.source = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_streamExpr);
						setState(183);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(184);
						match(DIV);
						setState(185);
						((StreamExprContext)_localctx).numeric = numeric();
						 ((StreamExprContext)_localctx).op =  new Divide(((StreamExprContext)_localctx).source.op, ((StreamExprContext)_localctx).numeric.value); 
						}
						break;
					case 3:
						{
						_localctx = new StreamExprContext(_parentctx, _parentState);
						_localctx.source = _prevctx;
						_localctx.source = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_streamExpr);
						setState(188);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(189);
						match(MOD);
						setState(190);
						((StreamExprContext)_localctx).INT = match(INT);
						 ((StreamExprContext)_localctx).op =  new Modulo(((StreamExprContext)_localctx).source.op, Long.parseLong((((StreamExprContext)_localctx).INT!=null?((StreamExprContext)_localctx).INT.getText():null))); 
						}
						break;
					case 4:
						{
						_localctx = new StreamExprContext(_parentctx, _parentState);
						_localctx.source = _prevctx;
						_localctx.source = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_streamExpr);
						setState(192);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(193);
						match(ADD);
						setState(194);
						((StreamExprContext)_localctx).numeric = numeric();
						 ((StreamExprContext)_localctx).op =  new Add(((StreamExprContext)_localctx).source.op, ((StreamExprContext)_localctx).numeric.value); 
						}
						break;
					case 5:
						{
						_localctx = new StreamExprContext(_parentctx, _parentState);
						_localctx.source = _prevctx;
						_localctx.source = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_streamExpr);
						setState(197);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(198);
						match(SUB);
						setState(199);
						((StreamExprContext)_localctx).numeric = numeric();
						 ((StreamExprContext)_localctx).op =  new Subtract(((StreamExprContext)_localctx).source.op, ((StreamExprContext)_localctx).numeric.value); 
						}
						break;
					case 6:
						{
						_localctx = new StreamExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_streamExpr);
						setState(202);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(203);
						match(SUB);
						setState(204);
						((StreamExprContext)_localctx).numeric = numeric();
						 ((StreamExprContext)_localctx).op =  new Subtract(((StreamExprContext)_localctx).source.op, ((StreamExprContext)_localctx).numeric.value); 
						}
						break;
					}
					} 
				}
				setState(211);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class IdPathContext extends ParserRuleContext {
		public List<String> path;
		public Token ID;
		public Token link;
		public List<TerminalNode> ID() { return getTokens(FilterExprParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(FilterExprParser.ID, i);
		}
		public IdPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idPath; }
	}

	public final IdPathContext idPath() throws RecognitionException {
		IdPathContext _localctx = new IdPathContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_idPath);
		 ((IdPathContext)_localctx).path =  new ArrayList<String>(); 
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			((IdPathContext)_localctx).ID = match(ID);
			 _localctx.path.add((((IdPathContext)_localctx).ID!=null?((IdPathContext)_localctx).ID.getText():null)); 
			setState(219);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(214);
					match(T__3);
					setState(215);
					((IdPathContext)_localctx).link = match(ID);
					 _localctx.path.add((((IdPathContext)_localctx).link!=null?((IdPathContext)_localctx).link.getText():null)); 
					}
					} 
				}
				setState(221);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public Comparable value;
		public NumericContext numeric;
		public StringExprContext stringExpr;
		public NumericContext numeric() {
			return getRuleContext(NumericContext.class,0);
		}
		public StringExprContext stringExpr() {
			return getRuleContext(StringExprContext.class,0);
		}
		public TerminalNode NOW() { return getToken(FilterExprParser.NOW, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_literal);
		try {
			setState(232);
			switch (_input.LA(1)) {
			case INT:
			case FLOAT:
				enterOuterAlt(_localctx, 1);
				{
				setState(222);
				((LiteralContext)_localctx).numeric = numeric();
				 ((LiteralContext)_localctx).value =  ((LiteralContext)_localctx).numeric.value; 
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(225);
				((LiteralContext)_localctx).stringExpr = stringExpr();
				 ((LiteralContext)_localctx).value =  ((LiteralContext)_localctx).stringExpr.value; 
				}
				break;
			case NOW:
				enterOuterAlt(_localctx, 3);
				{
				setState(228);
				match(NOW);
				setState(229);
				match(T__0);
				setState(230);
				match(T__1);
				 ((LiteralContext)_localctx).value =  Instant.now(); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GeoLiteralContext extends ParserRuleContext {
		public STGeometry value;
		public WktGeometryContext wktGeometry;
		public WktGeometryContext wktGeometry() {
			return getRuleContext(WktGeometryContext.class,0);
		}
		public GeoLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_geoLiteral; }
	}

	public final GeoLiteralContext geoLiteral() throws RecognitionException {
		GeoLiteralContext _localctx = new GeoLiteralContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_geoLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(234);
			((GeoLiteralContext)_localctx).wktGeometry = wktGeometry();
			 ((GeoLiteralContext)_localctx).value =  new STGeometry(((GeoLiteralContext)_localctx).wktGeometry.value); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WktGeometryContext extends ParserRuleContext {
		public Geometry value;
		public CoordContext c;
		public CoordSeqContext seq;
		public CoordSeqListContext seqList;
		public CoordContext coord() {
			return getRuleContext(CoordContext.class,0);
		}
		public CoordSeqContext coordSeq() {
			return getRuleContext(CoordSeqContext.class,0);
		}
		public CoordSeqListContext coordSeqList() {
			return getRuleContext(CoordSeqListContext.class,0);
		}
		public WktGeometryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_wktGeometry; }
	}

	public final WktGeometryContext wktGeometry() throws RecognitionException {
		WktGeometryContext _localctx = new WktGeometryContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_wktGeometry);
		try {
			setState(251);
			switch (_input.LA(1)) {
			case T__4:
				enterOuterAlt(_localctx, 1);
				{
				setState(237);
				match(T__4);
				setState(238);
				match(T__0);
				setState(239);
				((WktGeometryContext)_localctx).c = coord();
				setState(240);
				match(T__1);
				 ((WktGeometryContext)_localctx).value =  m_geomFactory.createPoint(((WktGeometryContext)_localctx).c.value); 
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 2);
				{
				setState(243);
				match(T__5);
				setState(244);
				((WktGeometryContext)_localctx).seq = coordSeq();
				 ((WktGeometryContext)_localctx).value =  m_geomFactory.createLineString(((WktGeometryContext)_localctx).seq.value); 
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 3);
				{
				setState(247);
				match(T__6);
				setState(248);
				((WktGeometryContext)_localctx).seqList = coordSeqList();

						LinearRing shell = m_geomFactory.createLinearRing(((WktGeometryContext)_localctx).seqList.value.get(0));
						if ( ((WktGeometryContext)_localctx).seqList.value.size() == 1 ) {
							((WktGeometryContext)_localctx).value =  m_geomFactory.createPolygon(shell, null);
						}
						else {
							LinearRing[] holes = ((WktGeometryContext)_localctx).seqList.value.stream()
														.skip(1)
														.map(seq -> m_geomFactory.createLinearRing(seq))
														.toArray(size -> new LinearRing[size]);
							((WktGeometryContext)_localctx).value =  m_geomFactory.createPolygon(shell, holes);
						}
					
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CoordSeqListContext extends ParserRuleContext {
		public List<CoordinateSequence> value;
		public CoordSeqContext h;
		public CoordSeqContext t;
		public List<CoordSeqContext> coordSeq() {
			return getRuleContexts(CoordSeqContext.class);
		}
		public CoordSeqContext coordSeq(int i) {
			return getRuleContext(CoordSeqContext.class,i);
		}
		public CoordSeqListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_coordSeqList; }
	}

	public final CoordSeqListContext coordSeqList() throws RecognitionException {
		CoordSeqListContext _localctx = new CoordSeqListContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_coordSeqList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			match(T__0);
			setState(254);
			((CoordSeqListContext)_localctx).h = coordSeq();
			 ((CoordSeqListContext)_localctx).value =  new ArrayList<CoordinateSequence>(); _localctx.value.add(((CoordSeqListContext)_localctx).h.value); 
			setState(262);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(256);
				match(T__2);
				setState(257);
				((CoordSeqListContext)_localctx).t = coordSeq();
				 _localctx.value.add(((CoordSeqListContext)_localctx).t.value); 
				}
				}
				setState(264);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(265);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CoordSeqContext extends ParserRuleContext {
		public CoordinateSequence value;
		public List<Coordinate> coordsList =  new ArrayList<Coordinate>();
		public CoordContext h;
		public CoordContext t;
		public List<CoordContext> coord() {
			return getRuleContexts(CoordContext.class);
		}
		public CoordContext coord(int i) {
			return getRuleContext(CoordContext.class,i);
		}
		public CoordSeqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_coordSeq; }
	}

	public final CoordSeqContext coordSeq() throws RecognitionException {
		CoordSeqContext _localctx = new CoordSeqContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_coordSeq);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			match(T__0);
			setState(268);
			((CoordSeqContext)_localctx).h = coord();
			 _localctx.coordsList.add(((CoordSeqContext)_localctx).h.value); 
			setState(276);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(270);
				match(T__2);
				setState(271);
				((CoordSeqContext)_localctx).t = coord();
				 _localctx.coordsList.add(((CoordSeqContext)_localctx).t.value); 
				}
				}
				setState(278);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(279);
			match(T__1);
			}

				Coordinate[] coords = _localctx.coordsList.toArray(new Coordinate[_localctx.coordsList.size()]);
				((CoordSeqContext)_localctx).value =  m_geomFactory.getCoordinateSequenceFactory().create(coords);

		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CoordContext extends ParserRuleContext {
		public Coordinate value;
		public double zval = 0;
		public CoordSegContext x;
		public CoordSegContext y;
		public CoordSegContext z;
		public List<CoordSegContext> coordSeg() {
			return getRuleContexts(CoordSegContext.class);
		}
		public CoordSegContext coordSeg(int i) {
			return getRuleContext(CoordSegContext.class,i);
		}
		public CoordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_coord; }
	}

	public final CoordContext coord() throws RecognitionException {
		CoordContext _localctx = new CoordContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_coord);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(281);
			((CoordContext)_localctx).x = coordSeg();
			setState(282);
			((CoordContext)_localctx).y = coordSeg();
			setState(286);
			_la = _input.LA(1);
			if (_la==INT || _la==FLOAT) {
				{
				setState(283);
				((CoordContext)_localctx).z = coordSeg();
				 ((CoordContext)_localctx).zval =  ((CoordContext)_localctx).z.value; 
				}
			}

			 ((CoordContext)_localctx).value =  new Coordinate(((CoordContext)_localctx).x.value, ((CoordContext)_localctx).y.value, _localctx.zval); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CoordSegContext extends ParserRuleContext {
		public double value;
		public Token INT;
		public Token FLOAT;
		public TerminalNode INT() { return getToken(FilterExprParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(FilterExprParser.FLOAT, 0); }
		public CoordSegContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_coordSeg; }
	}

	public final CoordSegContext coordSeg() throws RecognitionException {
		CoordSegContext _localctx = new CoordSegContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_coordSeg);
		try {
			setState(294);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(290);
				((CoordSegContext)_localctx).INT = match(INT);
				 ((CoordSegContext)_localctx).value =  Double.parseDouble((((CoordSegContext)_localctx).INT!=null?((CoordSegContext)_localctx).INT.getText():null)); 
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(292);
				((CoordSegContext)_localctx).FLOAT = match(FLOAT);
				 ((CoordSegContext)_localctx).value =  Double.parseDouble((((CoordSegContext)_localctx).FLOAT!=null?((CoordSegContext)_localctx).FLOAT.getText():null)); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumericContext extends ParserRuleContext {
		public Comparable value;
		public IntExprContext intExpr;
		public Token FLOAT;
		public IntExprContext intExpr() {
			return getRuleContext(IntExprContext.class,0);
		}
		public TerminalNode FLOAT() { return getToken(FilterExprParser.FLOAT, 0); }
		public NumericContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numeric; }
	}

	public final NumericContext numeric() throws RecognitionException {
		NumericContext _localctx = new NumericContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_numeric);
		try {
			setState(301);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(296);
				((NumericContext)_localctx).intExpr = intExpr();
				 ((NumericContext)_localctx).value =  ((NumericContext)_localctx).intExpr.value; 
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(299);
				((NumericContext)_localctx).FLOAT = match(FLOAT);
				 ((NumericContext)_localctx).value =  Double.parseDouble((((NumericContext)_localctx).FLOAT!=null?((NumericContext)_localctx).FLOAT.getText():null)); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntExprContext extends ParserRuleContext {
		public long value;
		public Token INT;
		public TerminalNode INT() { return getToken(FilterExprParser.INT, 0); }
		public IntExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intExpr; }
	}

	public final IntExprContext intExpr() throws RecognitionException {
		IntExprContext _localctx = new IntExprContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_intExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(303);
			((IntExprContext)_localctx).INT = match(INT);
			 ((IntExprContext)_localctx).value =  Long.parseLong((((IntExprContext)_localctx).INT!=null?((IntExprContext)_localctx).INT.getText():null)); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringExprContext extends ParserRuleContext {
		public String value;
		public Token STRING;
		public TerminalNode STRING() { return getToken(FilterExprParser.STRING, 0); }
		public StringExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringExpr; }
	}

	public final StringExprContext stringExpr() throws RecognitionException {
		StringExprContext _localctx = new StringExprContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_stringExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(306);
			((StringExprContext)_localctx).STRING = match(STRING);
			 ((StringExprContext)_localctx).value =  (((StringExprContext)_localctx).STRING!=null?((StringExprContext)_localctx).STRING.getText():null).substring(1, (((StringExprContext)_localctx).STRING!=null?((StringExprContext)_localctx).STRING.getText():null).length()-1); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 4:
			return streamExpr_sempred((StreamExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean streamExpr_sempred(StreamExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 20);
		case 1:
			return precpred(_ctx, 19);
		case 2:
			return precpred(_ctx, 18);
		case 3:
			return precpred(_ctx, 17);
		case 4:
			return precpred(_ctx, 16);
		case 5:
			return precpred(_ctx, 15);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3H\u0138\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3"+
		"\66\n\3\3\4\5\49\n\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4B\n\4\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5P\n\5\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\5\6\u00b3\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\7\6\u00d2\n\6\f\6\16\6\u00d5\13\6\3\7\3\7\3\7\3\7\3\7\7\7\u00dc\n"+
		"\7\f\7\16\7\u00df\13\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u00eb"+
		"\n\b\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\5\n\u00fe\n\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\7\13\u0107\n\13\f"+
		"\13\16\13\u010a\13\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\7\f\u0115"+
		"\n\f\f\f\16\f\u0118\13\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\5\r\u0121\n\r\3\r"+
		"\3\r\3\16\3\16\3\16\3\16\5\16\u0129\n\16\3\17\3\17\3\17\3\17\3\17\5\17"+
		"\u0130\n\17\3\20\3\20\3\20\3\21\3\21\3\21\3\21\2\3\n\22\2\4\6\b\n\f\16"+
		"\20\22\24\26\30\32\34\36 \2\6\3\2\21\26\3\28@\4\2%*,-\3\2\61\63\u014b"+
		"\2\"\3\2\2\2\4\65\3\2\2\2\6A\3\2\2\2\bO\3\2\2\2\n\u00b2\3\2\2\2\f\u00d6"+
		"\3\2\2\2\16\u00ea\3\2\2\2\20\u00ec\3\2\2\2\22\u00fd\3\2\2\2\24\u00ff\3"+
		"\2\2\2\26\u010d\3\2\2\2\30\u011b\3\2\2\2\32\u0128\3\2\2\2\34\u012f\3\2"+
		"\2\2\36\u0131\3\2\2\2 \u0134\3\2\2\2\"#\5\4\3\2#\3\3\2\2\2$%\7\31\2\2"+
		"%&\5\6\4\2&\'\b\3\1\2\'\66\3\2\2\2()\5\6\4\2)*\7\27\2\2*+\5\6\4\2+,\b"+
		"\3\1\2,\66\3\2\2\2-.\5\6\4\2./\7\30\2\2/\60\5\6\4\2\60\61\b\3\1\2\61\66"+
		"\3\2\2\2\62\63\5\6\4\2\63\64\b\3\1\2\64\66\3\2\2\2\65$\3\2\2\2\65(\3\2"+
		"\2\2\65-\3\2\2\2\65\62\3\2\2\2\66\5\3\2\2\2\679\7\13\2\28\67\3\2\2\28"+
		"9\3\2\2\29:\3\2\2\2:;\5\b\5\2;<\b\4\1\2<B\3\2\2\2=>\7\n\2\2>?\5\b\5\2"+
		"?@\b\4\1\2@B\3\2\2\2A8\3\2\2\2A=\3\2\2\2B\7\3\2\2\2CD\5\n\6\2DE\t\2\2"+
		"\2EF\5\16\b\2FG\b\5\1\2GP\3\2\2\2HI\5\n\6\2IJ\t\3\2\2JK\7\3\2\2KL\5\20"+
		"\t\2LM\7\4\2\2MN\b\5\1\2NP\3\2\2\2OC\3\2\2\2OH\3\2\2\2P\t\3\2\2\2QR\b"+
		"\6\1\2RS\5\f\7\2ST\b\6\1\2T\u00b3\3\2\2\2UV\7\35\2\2VW\7\3\2\2WX\5\n\6"+
		"\2XY\7\4\2\2YZ\b\6\1\2Z\u00b3\3\2\2\2[\\\7\36\2\2\\]\7\3\2\2]^\5\n\6\2"+
		"^_\7\5\2\2_`\5 \21\2`a\7\4\2\2ab\b\6\1\2b\u00b3\3\2\2\2cd\7\36\2\2de\7"+
		"\3\2\2ef\5 \21\2fg\7\5\2\2gh\5\n\6\2hi\7\4\2\2ij\b\6\1\2j\u00b3\3\2\2"+
		"\2kl\7\37\2\2lm\7\3\2\2mn\5\n\6\2no\7\5\2\2op\5\36\20\2pq\7\4\2\2qr\b"+
		"\6\1\2r\u00b3\3\2\2\2st\7 \2\2tu\7\3\2\2uv\5\n\6\2vw\7\4\2\2wx\b\6\1\2"+
		"x\u00b3\3\2\2\2yz\7!\2\2z{\7\3\2\2{|\5\n\6\2|}\7\4\2\2}~\b\6\1\2~\u00b3"+
		"\3\2\2\2\177\u0080\7\"\2\2\u0080\u0081\7\3\2\2\u0081\u0082\5\n\6\2\u0082"+
		"\u0083\7\4\2\2\u0083\u0084\b\6\1\2\u0084\u00b3\3\2\2\2\u0085\u0086\7#"+
		"\2\2\u0086\u0087\7\3\2\2\u0087\u0088\5\n\6\2\u0088\u0089\7\5\2\2\u0089"+
		"\u008a\5 \21\2\u008a\u008b\7\4\2\2\u008b\u008c\b\6\1\2\u008c\u00b3\3\2"+
		"\2\2\u008d\u008e\7#\2\2\u008e\u008f\7\3\2\2\u008f\u0090\5 \21\2\u0090"+
		"\u0091\7\5\2\2\u0091\u0092\5\n\6\2\u0092\u0093\7\4\2\2\u0093\u0094\b\6"+
		"\1\2\u0094\u00b3\3\2\2\2\u0095\u0096\7\66\2\2\u0096\u0097\7\3\2\2\u0097"+
		"\u0098\5\n\6\2\u0098\u0099\7\5\2\2\u0099\u009a\5 \21\2\u009a\u009b\7\4"+
		"\2\2\u009b\u00b3\3\2\2\2\u009c\u009d\7\67\2\2\u009d\u009e\7\3\2\2\u009e"+
		"\u009f\5\n\6\2\u009f\u00a0\7\4\2\2\u00a0\u00b3\3\2\2\2\u00a1\u00a2\7\3"+
		"\2\2\u00a2\u00a3\5\n\6\2\u00a3\u00a4\7\4\2\2\u00a4\u00a5\b\6\1\2\u00a5"+
		"\u00b3\3\2\2\2\u00a6\u00a7\t\4\2\2\u00a7\u00a8\7\3\2\2\u00a8\u00a9\5\n"+
		"\6\2\u00a9\u00aa\7\4\2\2\u00aa\u00ab\b\6\1\2\u00ab\u00b3\3\2\2\2\u00ac"+
		"\u00ad\t\5\2\2\u00ad\u00ae\7\3\2\2\u00ae\u00af\5\n\6\2\u00af\u00b0\7\4"+
		"\2\2\u00b0\u00b1\b\6\1\2\u00b1\u00b3\3\2\2\2\u00b2Q\3\2\2\2\u00b2U\3\2"+
		"\2\2\u00b2[\3\2\2\2\u00b2c\3\2\2\2\u00b2k\3\2\2\2\u00b2s\3\2\2\2\u00b2"+
		"y\3\2\2\2\u00b2\177\3\2\2\2\u00b2\u0085\3\2\2\2\u00b2\u008d\3\2\2\2\u00b2"+
		"\u0095\3\2\2\2\u00b2\u009c\3\2\2\2\u00b2\u00a1\3\2\2\2\u00b2\u00a6\3\2"+
		"\2\2\u00b2\u00ac\3\2\2\2\u00b3\u00d3\3\2\2\2\u00b4\u00b5\f\26\2\2\u00b5"+
		"\u00b6\7\16\2\2\u00b6\u00b7\5\34\17\2\u00b7\u00b8\b\6\1\2\u00b8\u00d2"+
		"\3\2\2\2\u00b9\u00ba\f\25\2\2\u00ba\u00bb\7\17\2\2\u00bb\u00bc\5\34\17"+
		"\2\u00bc\u00bd\b\6\1\2\u00bd\u00d2\3\2\2\2\u00be\u00bf\f\24\2\2\u00bf"+
		"\u00c0\7\20\2\2\u00c0\u00c1\7C\2\2\u00c1\u00d2\b\6\1\2\u00c2\u00c3\f\23"+
		"\2\2\u00c3\u00c4\7\f\2\2\u00c4\u00c5\5\34\17\2\u00c5\u00c6\b\6\1\2\u00c6"+
		"\u00d2\3\2\2\2\u00c7\u00c8\f\22\2\2\u00c8\u00c9\7\r\2\2\u00c9\u00ca\5"+
		"\34\17\2\u00ca\u00cb\b\6\1\2\u00cb\u00d2\3\2\2\2\u00cc\u00cd\f\21\2\2"+
		"\u00cd\u00ce\7\r\2\2\u00ce\u00cf\5\34\17\2\u00cf\u00d0\b\6\1\2\u00d0\u00d2"+
		"\3\2\2\2\u00d1\u00b4\3\2\2\2\u00d1\u00b9\3\2\2\2\u00d1\u00be\3\2\2\2\u00d1"+
		"\u00c2\3\2\2\2\u00d1\u00c7\3\2\2\2\u00d1\u00cc\3\2\2\2\u00d2\u00d5\3\2"+
		"\2\2\u00d3\u00d1\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\13\3\2\2\2\u00d5\u00d3"+
		"\3\2\2\2\u00d6\u00d7\7B\2\2\u00d7\u00dd\b\7\1\2\u00d8\u00d9\7\6\2\2\u00d9"+
		"\u00da\7B\2\2\u00da\u00dc\b\7\1\2\u00db\u00d8\3\2\2\2\u00dc\u00df\3\2"+
		"\2\2\u00dd\u00db\3\2\2\2\u00dd\u00de\3\2\2\2\u00de\r\3\2\2\2\u00df\u00dd"+
		"\3\2\2\2\u00e0\u00e1\5\34\17\2\u00e1\u00e2\b\b\1\2\u00e2\u00eb\3\2\2\2"+
		"\u00e3\u00e4\5 \21\2\u00e4\u00e5\b\b\1\2\u00e5\u00eb\3\2\2\2\u00e6\u00e7"+
		"\7$\2\2\u00e7\u00e8\7\3\2\2\u00e8\u00e9\7\4\2\2\u00e9\u00eb\b\b\1\2\u00ea"+
		"\u00e0\3\2\2\2\u00ea\u00e3\3\2\2\2\u00ea\u00e6\3\2\2\2\u00eb\17\3\2\2"+
		"\2\u00ec\u00ed\5\22\n\2\u00ed\u00ee\b\t\1\2\u00ee\21\3\2\2\2\u00ef\u00f0"+
		"\7\7\2\2\u00f0\u00f1\7\3\2\2\u00f1\u00f2\5\30\r\2\u00f2\u00f3\7\4\2\2"+
		"\u00f3\u00f4\b\n\1\2\u00f4\u00fe\3\2\2\2\u00f5\u00f6\7\b\2\2\u00f6\u00f7"+
		"\5\26\f\2\u00f7\u00f8\b\n\1\2\u00f8\u00fe\3\2\2\2\u00f9\u00fa\7\t\2\2"+
		"\u00fa\u00fb\5\24\13\2\u00fb\u00fc\b\n\1\2\u00fc\u00fe\3\2\2\2\u00fd\u00ef"+
		"\3\2\2\2\u00fd\u00f5\3\2\2\2\u00fd\u00f9\3\2\2\2\u00fe\23\3\2\2\2\u00ff"+
		"\u0100\7\3\2\2\u0100\u0101\5\26\f\2\u0101\u0108\b\13\1\2\u0102\u0103\7"+
		"\5\2\2\u0103\u0104\5\26\f\2\u0104\u0105\b\13\1\2\u0105\u0107\3\2\2\2\u0106"+
		"\u0102\3\2\2\2\u0107\u010a\3\2\2\2\u0108\u0106\3\2\2\2\u0108\u0109\3\2"+
		"\2\2\u0109\u010b\3\2\2\2\u010a\u0108\3\2\2\2\u010b\u010c\7\4\2\2\u010c"+
		"\25\3\2\2\2\u010d\u010e\7\3\2\2\u010e\u010f\5\30\r\2\u010f\u0116\b\f\1"+
		"\2\u0110\u0111\7\5\2\2\u0111\u0112\5\30\r\2\u0112\u0113\b\f\1\2\u0113"+
		"\u0115\3\2\2\2\u0114\u0110\3\2\2\2\u0115\u0118\3\2\2\2\u0116\u0114\3\2"+
		"\2\2\u0116\u0117\3\2\2\2\u0117\u0119\3\2\2\2\u0118\u0116\3\2\2\2\u0119"+
		"\u011a\7\4\2\2\u011a\27\3\2\2\2\u011b\u011c\5\32\16\2\u011c\u0120\5\32"+
		"\16\2\u011d\u011e\5\32\16\2\u011e\u011f\b\r\1\2\u011f\u0121\3\2\2\2\u0120"+
		"\u011d\3\2\2\2\u0120\u0121\3\2\2\2\u0121\u0122\3\2\2\2\u0122\u0123\b\r"+
		"\1\2\u0123\31\3\2\2\2\u0124\u0125\7C\2\2\u0125\u0129\b\16\1\2\u0126\u0127"+
		"\7D\2\2\u0127\u0129\b\16\1\2\u0128\u0124\3\2\2\2\u0128\u0126\3\2\2\2\u0129"+
		"\33\3\2\2\2\u012a\u012b\5\36\20\2\u012b\u012c\b\17\1\2\u012c\u0130\3\2"+
		"\2\2\u012d\u012e\7D\2\2\u012e\u0130\b\17\1\2\u012f\u012a\3\2\2\2\u012f"+
		"\u012d\3\2\2\2\u0130\35\3\2\2\2\u0131\u0132\7C\2\2\u0132\u0133\b\20\1"+
		"\2\u0133\37\3\2\2\2\u0134\u0135\7E\2\2\u0135\u0136\b\21\1\2\u0136!\3\2"+
		"\2\2\21\658AO\u00b2\u00d1\u00d3\u00dd\u00ea\u00fd\u0108\u0116\u0120\u0128"+
		"\u012f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}