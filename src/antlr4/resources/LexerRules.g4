lexer grammar CommonLexerRules;

	
ID		:	ID_LETTER (ID_LETTER | DIGIT)* ;
INT 	:	DIGIT+;
FLOAT	:
			DIGIT+ '.' DIGIT*
		|	'.' DIGIT+
		;
fragment ID_LETTER :	'a'..'z'|'A'..'Z'|'_' ;
fragment DIGIT :	'0'..'9' ;

STRING	:	'\'' (ESC|.)*? '\'' ;
fragment ESC :	'\\' [btnr"\\] ;

LINE_COMMENT:	'//' .*? '\n' -> skip ;
COMMENT		:	'/*' .*? '*/' -> skip ;

WS	:	[ \t\r\n]+ -> skip;