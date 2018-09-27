package algorithms;

import java.util.ArrayList;
import util.containers.*;
import util.*;

/**
 * K2Sat
 */
public class K2Sat extends AbstractAlgorithm {

    public K2Sat(ClauseArray clauseArr) {
        super(clauseArr);

        if (clauseArr.getKLevel() > 2) {
            System.out.println("USage of the 2-SAT solver algorithm is impossible : k > 2;");
            return;
        }
    }

    @Override
    public boolean solve() {
        ClauseArray clauseArr = this.clauseArr;

        while (clauseArr.size() > 0) {
            while (clauseArr.size() > 0) {
                Integer var = h(Flag.FirstUnique);
                if (var == null)
                    break;
            }

            Integer var = h(Flag.MostRepresentedVar);

        }
        return false;
    }

    protected boolean varValChangePossible(int var) {
        if (var > 0) {
            clauseArr.setVar(var, true);
        } else {
            clauseArr.setVar(-var, false);
        }
        if (!clauseArr.simplify(var, clauseArr.getVarVal().get(var)))
            return false;

        return true;
    }

    protected boolean tree(ArrayList<ArrayList<Integer>> clauseArr, int var, boolean val) {
        return false;
    }
}