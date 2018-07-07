package parselib;

import java.io.BufferedWriter;
import java.lang.ClassCastException;
import java.util.ArrayList;
import java.util.HashSet;

import algorithms.ClauseArray;

/**
 * Parser
 */
public class Parser {
    protected LookAhead1 reader;
    protected BufferedWriter bw;

    protected Boolean problemDefined;

    protected long clauseNb;

    protected ArrayList<HashSet<Integer>> clauses;

    public Parser(LookAhead1 l, BufferedWriter bw) {
        this.bw = bw;
        this.reader = l;
        problemDefined = false;
        this.clauseNb = 0;
        clauses = new ArrayList<>();
    }

    public void mainLoop() throws Exception{
        while(!reader.hasEnded()) {
            blockLine();
        }
        System.out.println("Solving...");
    }

    public void blockLine() throws Exception {
        Token t = reader.get();
        Symbol sym = t.getSymbol();
        HashSet<Integer> clause = null;

        switch(sym) {
            case COMMENT : commentLine(); break;
            case PROBLEM : problemLine(); break;
            case ENDL : reader.pop();
            case ZERO :
            case NUM :
                if(!problemDefined)
                    Main.err("Problem not defined prior to first clause.", 24);
                clause = clause();
                if(clause != null)
                    this.clauses.add(clause);
                break;
            default : System.out.println("Token not recognized : " + t.toString());

                System.exit(0);
        }
    }

    private void commentLine() throws Exception {
        String line = ((CommentToken)reader.pop()).getString();
        
        if (Main.getVerbose())
            System.out.println("c " + line);
    }

    private void problemLine() throws Exception {
        if(problemDefined)
            Main.err("Problem line already defined", 20);
        problemDefined = true;

        reader.eat(Symbol.PROBLEM);
        String line = "p ";

        if(!reader.check(Symbol.WORD))
            Main.err("Problem line has no problem type.", 21);

        line += ((WordToken) reader.pop()).getString() + " ";

        if(!reader.check(Symbol.NUM))
            Main.err("Problem line has no variable number defined.", 22);

        line += ((IntToken) reader.pop()).getValue() + " ";

        if (!reader.check(Symbol.NUM))
            Main.err("Problem line has no clause number defined.", 23);

        line += ((IntToken) reader.pop()).getValue();

        if(Main.getVerbose()) System.out.println(line);
        bw.write(line);
    }

    private HashSet<Integer> clause() throws Exception {
        clauseNb ++;

        HashSet<Integer> array = new ArrayList<>();

        Symbol sym = reader.get().getSymbol();

        String clause = "";

        if(sym == Symbol.ZERO) {
            if(Main.getVerbose()) System.out.println("II : Empty clause No " + clauseNb);
            return null;
        }

        boolean neg = false;
        int val;
        
        while(sym != Symbol.ZERO) {
            if(sym == Symbol.NUM) {
                val = ((IntToken) reader.pop()).getValue();
                if(neg) val = -val;
                array.add(val);
                clause += val + " ";
                neg = false;
            } else if (sym == Symbol.NEG) {
                neg = true;
                reader.pop();
            } else Main.err("Char in clause No " + Long.toString(clauseNb), 31);

            sym = reader.get().getSymbol();
        }

        reader.eat(Symbol.ZERO);

        if (Main.getVerbose())
            System.out.println(clause + "0");

        return new Clause(array);
    }

    public ClauseArray getClauses() {
        return clauses;
    }
}