package algorithms;

import java.util.ArrayList;
import java.util.Set;

public /**
 * Clause
 */
public class Clause extends Set<Integer> {
    static final long SerialVersionUID = 1293102118;

    boolean counted;

    public Clause() {
        super();
        counted = false;
    }

    public Clause(Set<Integer> clause) {
        super(clause);
    } 

    public void count() {
        for(int i : this.iterator())
            incrementVar(i);
    } 

    protected incrementValue(int var) {
        incrementValue(val, vars);
    }

    protected incrementValue(int var, HashMap<Integer, Counter> map) {
        if(!map.containsKey(val)) {
            map.put(val, new Counter(1));
        } else {
            map.get(val).increment();
        }
    }

    public int removeVar(int var) {
        for(int i = 0; i < this.size(); i ++)
            if(this.get(i) == var)
                this.remove(i);
    }
}