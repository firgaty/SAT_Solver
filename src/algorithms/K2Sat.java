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
        for(ArrayList<Integer> as : clauses) {
            if(as.size() == 1) {
                int var = as.get(0);
                if (var > 0)
                    values.put(var, true);
                else values.put(-var, false);
                if(!simplify(clauses, var, values.get(var)))
                    return false;
            }
        }

        return false;
    }

    protected boolean tree(ArrayList<ArrayList<Integer>> clauses, int var, boolean val) {
        
    }

    @Override
    public int h() {return 0;}

    public AbstractAlgorithm(ArrayList<ArrayList<Integer>> clauses, int varNb, int k_level) {
        this.clauses = clauses;
        this.varNb = varNb;
        this.k_level = k_level;
        this.values = new HashMap<>(varNb);
    }
}