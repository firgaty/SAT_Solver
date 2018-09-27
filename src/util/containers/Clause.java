package util.containers;

import java.util.TreeSet;

/**
 * Clause
 */
public class Clause extends TreeSet<Integer> {
    static final long SerialVersionUID = 1293102;

    public Clause() {
        super();
    }

    public Clause(TreeSet<Integer> hs) {
        super(hs);
    }

    /**
     * @return true if an element is present as pos and neg.
     */
    public boolean isAlwaysTrue() {
        for (Integer i : this) {
            if (i > 0)
                return false;
            if (this.contains(-i))
                return true;
        }
        return false;
    }

    public Integer firstPos() {
        return floor(1);
    }
}