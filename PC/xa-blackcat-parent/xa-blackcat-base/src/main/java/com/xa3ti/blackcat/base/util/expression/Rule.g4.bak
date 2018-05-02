
/** A Rule grammar for ANTLR v4 derived from ANTLR v3 Java grammar.
 *  Uses ANTLR v4's left-recursive expression notation.
 *  It parses ECJ, Netbeans, JDK etc...
 *
 *  Sam Harwell cleaned this up significantly and updated to 1.7!
 *
 *  You can test with
 *
 *  $ antlr4 Rule.g4
 */
grammar Rule;

// starting point for parsing a java file
compilationUnit
    :  expression
    ;

qualifiedName
    :   Identifier ('.' Identifier)*
    ;

literal
    :   IntegerLiteral
    |   FloatingPointLiteral
    |   CharacterLiteral
    |   StringLiteral
    |   BooleanLiteral
    |   'null'
    ;

literalString
    : '(' literal (',' literal)* ')'   
    ;

// EXPRESSIONS
expression
    :   primary
    |   basic
    |   expression '.' Identifier
    |   expression ('*'|'/'|'%') expression
    |   expression ('+'|'-') expression
    |   expression ('<=' | '>=' | '>' | '<') expression
    |   expression ('==' | '!=') expression
    |   expression '&&' expression
    |   expression '||' expression
    |   expression 'AND' expression
    |   expression 'and' expression
    |   expression 'OR' expression
    |   expression 'or' expression
    ;

primary
    :   '(' expression ')'   
    ;
    
basic
    :   qualifiedName ('<=' | '>=' | '>' | '<') literal
    |   qualifiedName ('==' | '!=' | '='  ) literal
    |   qualifiedName ('like' | 'LIKE') literal
    |   qualifiedName ('in' | 'IN') literalString
    |   qualifiedName ('findinset' | 'FINDINSET') literalString
    ;


// LEXER


// §3.10.1 Integer Literals

IntegerLiteral
    :   DecimalIntegerLiteral
    ;

fragment
DecimalIntegerLiteral
    :   DecimalNumeral IntegerTypeSuffix?
    ;



fragment
IntegerTypeSuffix
    :   [lL]
    ;

fragment
DecimalNumeral
    :   '0'
    |   NonZeroDigit (Digits? | Underscores Digits)
    ;

fragment
Digits
    :   Digit (DigitOrUnderscore* Digit)?
    ;

fragment
Digit
    :   '0'
    |   NonZeroDigit
    ;

fragment
NonZeroDigit
    :   [1-9]
    ;

fragment
DigitOrUnderscore
    :   Digit
    |   '_'
    ;

fragment
Underscores
    :   '_'+
    ;



// §3.10.2 Floating-Point Literals

FloatingPointLiteral
    :   DecimalFloatingPointLiteral
    ;

fragment
DecimalFloatingPointLiteral
    :   Digits '.' Digits? FloatTypeSuffix?
    |   '.' Digits FloatTypeSuffix?
    |   Digits FloatTypeSuffix?
    |   Digits FloatTypeSuffix
    ;


fragment
SignedInteger
    :   Sign? Digits
    ;

fragment
Sign
    :   [+-]
    ;

fragment
FloatTypeSuffix
    :   [fFdD]
    ;


// §3.10.3 Boolean Literals

BooleanLiteral
    :   'true'
    |   'false'
    ;

// §3.10.4 Character Literals

CharacterLiteral
    :   '\'' SingleCharacter '\''
    ;

fragment
SingleCharacter
    :   ~['\\]
    ;
    
    
// §3.10.5 String Literals
StringLiteral
    :   '"' StringCharacters? '"'
    |   '\'' StringCharacters? '\''
    ;
fragment
StringCharacters
    :   StringCharacter+
    ;


fragment
StringCharacter
    :   ~["\\]
    |   EscapeSequence
    ;

fragment
EscapeSequence
    :   '\\' [btnfr"'\\]
    |   OctalEscape
    |   UnicodeEscape
    ;
    
    
fragment
OctalEscape
    :   '\\' OctalDigit
    |   '\\' OctalDigit OctalDigit
    |   '\\' ZeroToThree OctalDigit OctalDigit
    ;

fragment
UnicodeEscape
    :   '\\' 'u' HexDigit HexDigit HexDigit HexDigit
    ;

fragment
ZeroToThree
    :   [0-3]
    ;
    
// §3.8 Identifiers (must appear after all keywords in the grammar)

Identifier
    :   JavaLetter JavaLetterOrDigit*
    ;


fragment
HexDigit
    :   [0-9a-fA-F]
    ;

fragment
OctalDigit
    :   [0-7]
    ;   
    
fragment
JavaLetter
    :   [a-zA-Z$_] // these are the "java letters" below 0xFF
    ;

fragment
JavaLetterOrDigit
    :   [a-zA-Z0-9$_] // these are the "java letters or digits" below 0xFF
    ;

//
// Additional symbols not defined in the lexical specification
//

AT : '@';
ELLIPSIS : '...';

//
// Whitespace and comments
//

WS  :  [ \t\r\n\u000C]+ -> skip
    ;

COMMENT
    :   '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    :   '//' ~[\r\n]* -> skip
    ;

