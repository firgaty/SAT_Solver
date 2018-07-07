package algorithms;

import java.util.ArrayList;

public /**
 * K2Sat
 */
public class K2Sat extends AbstractAlgorithm {

    public K2Sat(ArrayList<ArrayList<Integer>> clauses, int varNb, int k_level) {
        super(clauses, varNb, k_level);

        if(k_level > 2) {
            System.out.println("USage of the 2-SAT solver algorithm is impossible : k > 2;");
            return;
        }
    }

    @Override
    public boolean solve() {
        ArrayList<ArrayList<Integer>> clauses = this.clauses;

        while(clauses.size() > 0) {
            while (clauses.size() > 0) {
                Integer var = h(Flag.FirstUnique);
                if(var == null) break;
                if (var > 0) {
                    values.put(var, true);
                } else {
                    values.put(-var, false);
                }
                if(!simplify(clauses, var, values.get(var)))
                    return false;
            
            }

        }
        return false;
    }

    protected boolean tree(ArrayList<ArrayList<Integer>> clauses, int var, boolean val) {
        
    }
}