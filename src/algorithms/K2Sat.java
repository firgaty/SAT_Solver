package algorithms;

import java.util.ArrayList;
import util.containers.*;

public /**
 * K2Sat
 */
public class K2Sat extends AbstractAlgorithm {

    public K2Sat(ClauseArray clauseArr, int varNb, int k_level) {
        super(clauseArr, varNb, k_level);

        if(k_level > 2) {
            System.out.println("USage of the 2-SAT solver algorithm is impossible : k > 2;");
            return;
        }
    }

    @Override
    public boolean solve() {
        ClauseArray clauseArr = this.clauseArr;

        while(clauseArr.size() > 0) {
            while (clauseArr.size() > 0) {
                Integer var = h(Flag.FirstUnique);
                if(var == null) break;
                if (var > 0) {
                    values.put(var, true);
                } else {
                    values.put(-var, false);
                }
                if(!simplify(clauseArr, var, values.get(var)))
                    return false;
            
            }

        }
        return false;
    }

    protected boolean tree(ArrayList<ArrayList<Integer>> clauseArr, int var, boolean val) {
        
    }
}