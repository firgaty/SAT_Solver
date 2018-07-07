package util.containers;

import java.util.TreeSet;

public /**
 * Clause
 */
public class Clause extends TreeSet<Integer> {
    static final long SerialVersionUID = 1293102;

    public Clause() {
        super();
    }

    public Clause(TreeSe<Integer> hs) {
        super(hs);
    }

    public boolean isAlwaysTrue() {
        for(Integer i : this)
            if(i > 0) return false;
            if(this.contains(-i))
                return true;
        return false;
    }
}