package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import parselib.Main;

/**
 * AbstractAlgorithm
 */
abstract public class AbstractAlgorithm {

    protected ArrayList<ArrayList<Integer>> clauses;
    protected HashMap<Integer, Boolean> values;

    int varNb;
    int k_level;

    protected boolean sorted;

    AbstractAlgorithm(ArrayList<ArrayList<Integer>> clauses, int varNb, int k_level) {
        this.clauses = clauses;
        this.varNb = varNb;
        this.k_level = k_level;
        this.values = new HashMap<>(varNb);
    }

    abstract public boolean solve();
    abstract public int h();

    public void sortClauses() {
        for(ArrayList<Integer> as : clauses)
            as.sort((a, b) -> Integer.compare(a, b));
        clauses.sort((a, b) -> Integer.compare(a.size(), b.size()));

        if(Main.getVerbose())
            System.out.println("Sorted.");
    }
    
    public ArrayList<Integer> getSmallestClause() {
        if(sorted) return clauses.get(0);
        
        int min = clauses.get(0).size();
        int index = 0;

        for(int i = 1; i < clauses.size(); i ++) {
            if(clauses.get(i).size() < min) {
                min = clauses.get(i).size();
                index = i;
            }
        }

        if(Main.getVerbose()) {
            System.out.println("Smallest clause : " + index + " - size : " + min);
            printClause(clauses.get(index));
        }

        return clauses.get(index);
    }

    public ArrayList<Integer> getBiggestClause() {
        if (sorted)
            return clauses.get(clauses.size() - 1);

        int max = clauses.get(0).size();
        int index = 0;

        for (int i = 1; i < clauses.size(); i++) {
            if (clauses.get(i).size() > max) {
                max = clauses.get(i).size();
                index = i;
            }
        }

        if (Main.getVerbose()) {
            System.out.println("Biggest clause : " + index + " - size : " + max);
            printClause(clauses.get(index));
        }

        return clauses.get(index);
    }

    public static void printClause(ArrayList<Integer> clause) {
        String str = "[";
        
        for(int c : clause)
            str += c + ", ";
        
        str += "]";

        System.out.println(str);
    }

    public boolean isSorted() {
        return sorted;
    }
}