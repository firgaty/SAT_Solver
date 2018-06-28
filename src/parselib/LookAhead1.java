package parselib;
import java.io.IOException;

public class LookAhead1 {
    protected Token current;
    protected Lexer lexer;

    public LookAhead1(Lexer l) throws Exception, IOException {
        this.lexer = l;
        this.current = lexer.yylex();
    }

    public boolean hasEnded() {
        return (this.current == null);
    }

    public void eat(Symbol s) throws Exception, IOException {
        if (this.current.getSymbol() != s) {
            throw new Exception("Non-awaited Symbol. Cannot eat a " + s + " with a " + this.current.getSymbol());
        }
        // System.out.println(this.current);
        this.current = lexer.yylex();
    }

    public boolean check(Symbol s) {
        if (hasEnded())
            return false;
        return (this.current.getSymbol() == s);
    }

    public Token get() {
        return new Token(current.getSymbol());
    }

    public Token pop() throws Exception, IOException {
        Token t;
        t = this.current;
        this.current = lexer.yylex();
        // System.out.println(t);
        return t;
    }

    public Token pop(Symbol s) throws Exception, IOException {
        Token t;
        if (this.current.getSymbol() != s) {
            throw new Exception("Non-awaited Symbol. Cannot eat a " + s + " with a " + this.current.getSymbol());
        }
        t = this.current;
        this.current = lexer.yylex();
        // System.out.println(t);
        return t;
    }

}