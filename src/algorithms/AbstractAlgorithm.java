package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import parselib.Main;
import util.ErrPrint;
import util.containers.*;
import util.Counter;

/**
 * AbstractAlgorithm
 */
abstract public class AbstractAlgorithm {

    protected ClauseArray clauseArr;

    public AbstractAlgorithm(ClauseArray clauseArr) {
        this.clauseArr = clauseArr;
    }

    public AbstractAlgorithm(ClauseArray clauseArr, HashMap<Integer, Boolean> values, HashMap<Integer, Counter> valuesCount, int varNb, int k_level) {
        this.clauseArr = new ClauseArray(clauseArr, varNb, k_level);
    }

    abstract public boolean solve();
    
    public Integer h(Flag flag) {
        switch (flag) {
        case FirstUnique:
            return clauseArr.firstUnique();
        case MostRepresentedUnique:
            return clauseArr.mostRepresentedUnique();
        case MostRepresentedVar:
            return clauseArr.getMostRepresented();
        default:
            ErrPrint.err("Invalid Flag", 300);
        }

        return null;
    }
}