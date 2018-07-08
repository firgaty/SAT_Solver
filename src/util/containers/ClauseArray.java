package util.containers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import util.Counter;

import parselib.Main;

public /**
 * ClauseArray
 */
public class ClauseArray extends ArrayList<Clause> {
    static final long SerialVersionUID = 1293117;

    protected HashMap<Integer, Boolean> varVal;
    protected HashMap<Integer, Counter> varCount;

    int varNb;
    int k_level;

    protected boolean sorted;
    protected boolean counted;
    protected boolean initialized;

    public ClauseArray() {
        super();
        varVal = new HashMap<>();
        varCount = new HashMap<>();
        k_level = 0;
        varNb = 0;
        sorted = false;
        counted = false;
        initialized = false;
    }

    public ClauseArray(ArrayList<Clause> cl, int varNb, int k_level) {
        super(cl);
        this.varNb = varNb;
        this.k_level = k_level;
        this.varVal = new HashMap<>(varNb);
        this.varCount = new HashMap<>(varNb * 2);
        sorted = false;
        counted = false;
        initialized = false;
    }

    public ClauseArray(ArrayList<Clause> cl, int varNb, int k_level, HashMap<Integer, Boolean> varVal, HashMap<Integer, Counter> varCount, boolean sorted, boolean counted, boolean initialized) {
        super(cl);
        this.varNb = varNb;
        this.k_level = k_level;
        this.varVal = varVal;
        this.varCount = varCount;
        this.sorted = sorted;
        this.counted = counted;
        this.initialized = initialized;
    }

    public ClauseArray clone() {
        ArrayList<Clause> clausesClone = new ArrayList<>();

        for (Clause c : this)
            clauseClone.add(c.clone());

        return new ClauseArray(clausesClone, varVal.clone(), varCount.clone(), varNb, k_level, sorted, counted);
    }

    public void initialize() {
        if(initialized) return;

        varVal = new HashMap<>();
        varCount = new HashMap<>();

        int max = 0;

        for(int i = 0; i < this.size(); i ++) {
            if(this.get(i).isAlwaysTrue()) {
                this.remove(i);
                i --;
                continue;
            }
            if(this.get(i).size() > max) max = this.get(i).size();
            for(int j : this.get(i)) {
                incrementValue(j);
            }
        }

        counted = true;
        this.k_level = max;
        simplify(this, var, val);
        initialized = true;
    }

    public ClauseArray modifiedClone(int var, boolean val) {
        ClauseArray clone = clone();
        clone.simplify(var, val);
        return clone;
    }

    public boolean simplify(int var, boolean val) {
        simplify(this, var, val);
    }

    public boolean simplify(ClauseArray clauses, int var, boolean val) {
        // clauses.getValuesCount().get(var).reset();

        for (int i = 0; i < clauses.size(); i++) {
            if (clauses.get(i).contains(var) && val || clauses.get(i).contains(-var) && !val) {
                clauses.remove(i);
                i--;
                return true;
            } else if (clauses.get(i).contains(var) || clauses.get(i).contains(-var)) {
                if (clauses.get(i).size() <= 1)
                    return false;
                clauses.get(i).remove(var);
                i--;
                return true;
            }
        }

        return true;
    }

    public resetVar(int var) {
        this.varVal.remove(var);
    }

    public setVar(int var, Boolean val) {
        if(!varExists(var)) {
            if(Main.getVerbose())
                System.out.println("Value not contained.");
        } else
            varVal.replace(var, val);
        simplify(var, val);
    }

    public boolean varExists(int var) {
        return varVal.containsKey(var);
    }

    protected void mapVars() {
        for (Clause c : this)
            for (int i : c) 
                if(!varVal.containsKey(i))
                    varVal.put(i, null);
    }

    public void countValues() {
        for (Clause c : this)
            for (int i : c)
                incrementValue(i);
        counted = true;
    }

    protected incrementValue(int val) {
        incrementValue(val, varCount);
    }

    protected incrementValue(int val, HashMap<Integer, Counter> map) {
        if(!map.containsKey(val)) {
            map.put(val, new Counter(1));
        } else {
            map.get(val).increment();
        }
    }

    @Override
    public void sort() {
        this.sort((a, b) -> Integer.compare(a.size(), b.size()));

        if (Main.getVerbose())
            System.out.println("Sorted.");

        sorted = true;
    }

    public Clause getSmallestClause() {
        if (sorted)
            return this.get(0);

        int min = this.get(0).size();
        int index = 0;

        for (int i = 1; i < this.size(); i++) {
            if (this.get(i).size() < min) {
                min = this.get(i).size();
                index = i;
            }
        }

        if (Main.getVerbose()) {
            System.out.println("Smallest clause : " + index + " - size : " + min);
            printClause(this.get(index));
        }

        return this.get(index);
    }

    public Clause getBiggestClause() {
        if (sorted)
            return this.get(this.size() - 1);

        int max = this.get(0).size();
        int index = 0;

        for (int i = 1; i < this.size(); i++) {
            if (this.get(i).size() > max) {
                max = this.get(i).size();
                index = i;
            }
        }

        if (Main.getVerbose()) {
            System.out.println("Biggest clause : " + index + " - size : " + max);
            printClause(this.get(index));
        }

        return this.get(index);
    }

    public int getMostRepresented() {
        getMostRepresented(this.varCount);
    }

    public int getMostRepresented(HashMap<Integer, Counter> varCount) {
        int max = 0;
        int maxVal;

        for (Map<K, V>.Entry<Integer, Counter> entry : this.varCount.entrySet()) {
            if (entry.getValue().get() > max) {
                max = entry.getValue().get();
                maxVal = entry.getKey();
            }
        }

        return maxVal;
    }

    public int getMostRepresented(ArrayList<Integer> as) {
        return getMostRepresented(as, this.varCount, false);
    }

    public int getMostRepresented(ArrayList<Integer> as, HashMap<Integer, Counter> varCount) {
        return getMostRepresented(as, varCount, false);
    }

    public int getMostRepresented(ArrayList<Integer> as, HashMap<Integer, Counter> varCount, boolean strictPosNeg) {
        if (!counted)
            countValues();

        int max = varCount.get(as.get(0)).get();
        int maxVal = as.get(0);

        for (int i : as) {
            if (!strictPosNeg && varCount.get(i).get() > max) {
                max = varCount.get(i).get();
                maxVal = i;
            } else if (strictPosNeg && varCount.containsKey(-i)
                    && varCount.get(i).get() + varCount.get(-i).get() > max) {
                max = varCount.get(i).get() + varCount.get(-i).get();
                maxVal = i;
            }
        }

        return maxVal;
    }

    public Integer firstUnique() {
        if (!sorted)
            sort();

        for (Clause c : this)
            if (c.size() == 1)
                return c.get(0);
        return null;
    }

    public Integer mostRepresentedUnique() {
        if (!sorted)
            sort();

        HashMap<Integer, Counter> uniques = new HashMap<>();

        for(Clause c : this) {
            if(c.size() > 1) break;
            incrementValue(c.get(0), uniques);
        }

        return getMostRepresented(uniques);
    }

    public static void printClause(Clause clause) {
        String str = "[";

        for (int c : clause)
            str += c + ", ";

        str += "]";

        System.out.println(str);
    }

    public boolean isSorted() {
        return sorted;
    }

    public boolean isCounted() {
        return counted;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public HashMap<Integer, Boolean> getVarVal() {
        return varVal;
    }
    public HashMap<Integer, Counter> getVarCount() {
        return varCount;
    }

    public Clause remove(int index) {
        for(int i : this.get(index))
            this.varCount.get(i).decrement();
        
        sorted = false;
        return super.remove(index);
    }

    public void removeVar(int var) {
        if(!varCount.containsKey(var))
            return;

        for(Clause c : this)
            for(int i = 0; i < c.size(); i ++)
                if(c.get(i) == var)
                    c.remove(i);

        varCount.get(var).reset();
        sorted = false;
    } 

    @Override
    public boolean add(Clause c) {
        if(c.isAlwaysTrue())
            return false;
        if(c.size() > k_level)
            k_level = c.size();
        for(int i : c)
            this.varCount.get(i).decrement();
        return super.add(c);
    }
}