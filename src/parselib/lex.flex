package parselib;

%%
%public
%class Lexer
%unicode
%type Token
%line
%column

%yylexthrow LexerException

%{
public class LexerException extends Exception {

  public LexerException () {
    super("Lexer Exception, unexpected caracter found at line " + yyline + " and column " + yycolumn + ".");
  }
}
%}

number = [0-9][0-9]*
// operator = "+" | "-" | "/" | "*"
blank = "\n" | "\r" | " " | "\t"
alpha = [a-zA-Z]
line = .*
word = {alpha}{alpha}*

%state COMMENT
%%
<YYINITIAL> {
    "c "            {yybegin(COMMENT);}
    "p "            {return new Token(Symbol.PROBLEM);}
    "-"             {return new Token(Symbol.NEG);}
    ","             {return new Token(Symbol.COMMA);}
    ";"             {return new Token(Symbol.SEMICOLON);}
    ":"             {return new Token(Symbol.COLON);}
    "0 "|"0\n"      {return new Token(Symbol.ZERO);}
    {word}          {return new WordToken(yytext());}
    {number}        {return new IntToken(Integer.parseInt(yytext()));}
    "\n"            {return new Token(Symbol.ENDL);}
    {blank}         {}
    [^]             {throw new LexerException();}
}
<COMMENT> {
    "\n"            {yybegin(YYINITIAL);}
    {line}          {return new CommentToken(yytext());}
}

