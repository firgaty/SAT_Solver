package parselib;

class Token {
    protected Symbol sym;

    public Token(Symbol s) {
        sym = s;
    }

    public Symbol getSymbol() {
        return sym;
    }

    public String toString() {
        return ("Symbol : " + this.sym);
    }
}

// class OpToken extends Token {
//     protected String operator;

//     public OpToken(String ope) {
//         super(Symbol.OP);
//         operator = ope;
//     }

//     public int apply(int a, int b) throws Exception {
//         switch (operator) {
//         case "+":
//             return (a + b);
//         case "-":
//             return (a - b);
//         case "/":
//             return (a / b);
//         case "*":
//             return (a * b);
//         default:
//             throw new Exception("Should Not Happen : Missmatched Operator");
//         }
//     }

//     public Op getOp() throws Exception {
//         switch (operator) {
//         case "+":
//             return Op.ADD;
//         case "-":
//             return Op.SUB;
//         case "/":
//             return Op.DIV;
//         case "*":
//             return Op.MUL;
//         default:
//             throw new Exception("Should Not Happen : Missmatched Operator");
//         }
//     }

//     public String toString() {
//         return (super.toString() + " " + operator);
//     }
// }

class IntToken extends Token {
    protected int value;

    public IntToken(int i) {
        super(Symbol.NUM);
        value = i;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return (super.toString() + " " + value);
    }
}

class WordToken extends Token {
    protected String str;

    public WordToken(String str) {
        super(Symbol.WORD);
        this.str = str;
    }

    public String getString() {
        return str;
    }

    public String toString() {
        return (super.toString() + " " + str);
    }
}

class LineToken extends Token {
    protected String str;

    public LineToken(String str) {
        super(Symbol.LINE);
        this.str = str;
    }

    public String getString() {
        return str;
    }

    public String toString() {
        return (super.toString() + " " + str);
    }
}

class CommentToken extends Token {
    protected String str;

    public CommentToken(String str) {
        super(Symbol.COMMENT);
        this.str = str;
    }

    public String getString() {
        return str;
    }

    public String toString() {
        return (super.toString() + " " + str);
    }
}
