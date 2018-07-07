package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import parselib.Main;
import util.ErrPrint;

/**
 * AbstractAlgorithm
 */
abstract public class AbstractAlgorithm {

    protected ClauseArray clauses;

    public AbstractAlgorithm(ClauseArray clauses) {
        this.clauses = clauses;
    }

    public AbstractAlgorithm(ArrayList<ArrayList<Integer>> clauses, HashMap<Integer, Boolean> values, HashMap<Integer, Counter> valuesCount, int varNb, int k_level) {
        this.clauses = new ClauseArray(clauses, varNb, k_level);
    }

    abstract public boolean solve();
    
    public Integer h(Flag flag) {
        switch (flag) {
        case FirstUnique:
            return clauses.firstUnique();
            break;
        case MostRepresentedUnique:
            return clauses.mostRepresentedUnique();
            break;
        case MostRepresentedVar:
            return clauses.getMostRepresented();
            break;
        default:
            ErrPrint.err("Invalid Flag", 300);
        }
    }
}