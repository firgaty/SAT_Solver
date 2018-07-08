package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import parselib.Main;
import util.ErrPrint;
import util.containers.*;

/**
 * AbstractAlgorithm
 */
abstract public class AbstractAlgorithm {

    protected ClauseArray clauseArr;

    public AbstractAlgorithm(ClauseArray clauseArr) {
        this.clauseArr = clauseArr;
    }

    public AbstractAlgorithm(ArrayList<ArrayList<Integer>> clauseArr, HashMap<Integer, Boolean> values, HashMap<Integer, Counter> valuesCount, int varNb, int k_level) {
        this.clauseArr = new ClauseArray(clauseArr, varNb, k_level);
    }

    abstract public boolean solve();
    
    public Integer h(Flag flag) {
        switch (flag) {
        case FirstUnique:
            return clauseArr.firstUnique();
            break;
        case MostRepresentedUnique:
            return clauseArr.mostRepresentedUnique();
            break;
        case MostRepresentedVar:
            return clauseArr.getMostRepresented();
            break;
        default:
            ErrPrint.err("Invalid Flag", 300);
        }
    }
}