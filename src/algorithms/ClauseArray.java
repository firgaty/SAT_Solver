import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import parselib.Main;

public /**
 * ClauseArray
 */
public class ClauseArray extends ArrayList<Set<Integer>> {
    static final long SerialVersionUID = 1293102117;

    protected HashMap<Integer, Boolean> values;
    protected HashMap<Integer, Counter> valuesCount;

    int varNb;
    int k_level;

    protected boolean sorted;
    protected boolean counted;
    protected boolean initialized;

    public ClauseArray() {
        super();
        values = new HashMap<>();
        valuesCount = new HashMap<>();
        k_level = 0;
        varNb = 0;
        sorted = false;
        counted = false;
        initialized = false;
    }

    public ClauseArray(ArrayList<Set<Integer>> cl, int varNb, int k_level) {
        super(cl);
        this.varNb = varNb;
        this.k_level = k_level;
        this.values = new HashMap<>(varNb);
        this.valuesCount = new HashMap<>(varNb * 2);
        sorted = false;
        counted = false;
        initialized = false;
    }

    public ClauseArray(ArrayList<Set<Integer>> cl, int varNb, int k_level, HashMap<Integer, Boolean> values, HashMap<Integer, Counter> valuesCount, boolean sorted, boolean counted, boolean initialized) {
        super(cl);
        this.varNb = varNb;
        this.k_level = k_level;
        this.values = values;
        this.valuesCount = valuesCount;
        this.sorted = sorted;
        this.counted = counted;
        this.initialized = initialized;
    }

    public ClauseArray clone() {
        ArrayList<ArrayList<Integer>> clausesClone = new ArrayList<>();

        for (Set<Integer> item : clauses)
            clauseClone.add(item.clone());

        return new ClauseArray(clausesClone, values.clone(), valuesCount.clone(), varNb, k_level, sorted, counted);
    }

    public void initialize() {
        if(initialized) return;

        int max = 0;

        for(Clause c : this) {
            if(c.size() > max) max = c.size();
            for(int i : c) {
                incrementValue(i);
            }
        }

        counted = true;
        this.k_level = max;
        simplify(clauses, var, val);
        initialized = true;
    }

    public ClauseArray modifiedClone(int var, boolean b) {
        ClauseArray clone = clone();
        clone.simplify(var, val);
        return clone;
    }

    public boolean simplify(int var, boolean b) {
        simplify(this, var, b);
    }

    public boolean simplify(ClauseArray clauses, int var, boolean val) {
        clauses.getValuesCount().get(var).reset();

        for (int i = 0; i < clauses.size(); i++) {
            if (clauses.get(i).contains(var) && val || clauses.get(i).contains(-var) && !val) {
                clauses.remove(i);
                return true;
            } else if (clauses.get(i).contains(var) || clauses.get(i).contains(-var)) {
                if (clauses.get(i).size() <= 1)
                    return false;
                clauses.get(i).remove(clauses.get(i).indexOf(var));
                clauses.getValuesCount().get(var).decrement(); /********************* */
                return true;
            }
        }

        return true;
    }

    public resetVar(int var) {
        this.values.remove(var);
    }

    public setVar(int var, Boolean b) {
        if(!values.containsKey(var)) {
            if(Main.getVerbose())
                System.out.println("Value not contained.");
        } else
            values.replace(var, b);
    }

    public boolean varExists(int var) {
        return values.containsKey(var);
    }

    protected void mapVars() {
        for (ArrayList<Integer> as : clauses)
            for (int i : as) 
                if(!values.containsKey(i))
                    values.put(i, null);
    }

    public void countValues() {
        for (ArrayList<Integer> as : clauses)
            for (int i : as)
                incrementValue(i);
        counted = true;
    }

    protected incrementValue(int val) {
        incrementValue(val, valuesCount);
    }

    protected incrementValue(int val, HashMap<Integer, Counter> map) {
        if(!map.containsKey(val)) {
            map.put(val, new Counter(1));
        } else {
            map.get(val).increment();
        }
    }

    public void sortClauses() {
        for (ArrayList<Integer> as : clauses)
            as.sort((a, b) -> Integer.compare(a, b));
        clauses.sort((a, b) -> Integer.compare(a.size(), b.size()));

        if (Main.getVerbose())
            System.out.println("Sorted.");
    }

    public ArrayList<Integer> getSmallestClause() {
        if (sorted)
            return clauses.get(0);

        int min = clauses.get(0).size();
        int index = 0;

        for (int i = 1; i < clauses.size(); i++) {
            if (clauses.get(i).size() < min) {
                min = clauses.get(i).size();
                index = i;
            }
        }

        if (Main.getVerbose()) {
            System.out.println("Smallest clause : " + index + " - size : " + min);
            printClause(clauses.get(index));
        }

        return clauses.get(index);
    }

    public ArrayList<Integer> getBiggestClause() {
        if (sorted)
            return clauses.get(clauses.size() - 1);

        int max = clauses.get(0).size();
        int index = 0;

        for (int i = 1; i < clauses.size(); i++) {
            if (clauses.get(i).size() > max) {
                max = clauses.get(i).size();
                index = i;
            }
        }

        if (Main.getVerbose()) {
            System.out.println("Biggest clause : " + index + " - size : " + max);
            printClause(clauses.get(index));
        }

        return clauses.get(index);
    }

    public int getMostRepresented() {
        getMostRepresented(this.valuesCount);
    }

    public int getMostRepresented(HashMap<Integer, Counter> valuesCount) {
        int max = 0;
        int maxVal;

        for (Map<K, V>.Entry<Integer, Counter> entry : this.valuesCount.entrySet()) {
            if (entry.getValue().get() > max) {
                max = entry.getValue().get();
                maxVal = entry.getKey();
            }
        }

        return maxVal;
    }

    public int getMostRepresented(ArrayList<Integer> as) {
        return getMostRepresented(as, this.valuesCount, false);
    }

    public int getMostRepresented(ArrayList<Integer> as, HashMap<Integer, Counter> valuesCount) {
        return getMostRepresented(as, valuesCount, false);
    }

    public int getMostRepresented(ArrayList<Integer> as, HashMap<Integer, Counter> valuesCount, boolean strictPosNeg) {
        if (!counted)
            countValues();

        int max = valuesCount.get(as.get(0)).get();
        int maxVal = as.get(0);

        for (int i : as) {
            if (!strictPosNeg && valuesCount.get(i).get() > max) {
                max = valuesCount.get(i).get();
                maxVal = i;
            } else if (strictPosNeg && valuesCount.containsKey(-i)
                    && valuesCount.get(i).get() + valuesCount.get(-i).get() > max) {
                max = valuesCount.get(i).get() + valuesCount.get(-i).get();
                maxVal = i;
            }
        }

        return maxVal;
    }

    public Integer firstUnique() {
        if (!sorted)
            sortClauses();

        for (ArrayList<Integer> as : clauses)
            if (as.size() == 1)
                return as.get(0);
        return null;
    }

    public Integer mostRepresentedUnique() {
        if (!sorted)
            sortClauses();

        HashMap<Integer, Counter> uniques = new HashMap<>();

        for(ArrayList<Integer> cs : clauses) {
            if(cs.size() > 1) break;
            incrementValue(cs.get(0), uniques);
        }

        return getMostRepresented(uniques);
    }

    public static void printClause(ArrayList<Integer> clause) {
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

    public HashMap<Integer, Boolean> getValues() {
        return values;
    }
    public HashMap<Integer, Counter> getValuesCount() {
        return valuesCount;
    }

    public ArrayList<ArrayList<Integer>> getClauses() {
        return clauses;
    }

    public void setClauses(ArrayList<ArrayList<Integer>> css) {
        this.clauses = css;
        sorted = false;
    }

    public void addClause(ArrayList<Integer> cs) {
        this.clauses.add(cs);
        sorted = false;
    }

    public void removeClause(int index) {
        this.clauses.remove(index);
        sorted = false;
    }

    public void removeVar(int var) {
        if(!valuesCount.containsKey(var))
            return;

        for(ArrayList<Integer> cs : clauses)
            for(int i = 0; i < cs.size(); i ++)
                if(cs.get(i) == var)
                    cs.remove(i);

        valuesCount.get(var).reset();
        sorted = false;
    }

    public int clauseArraySize() {
        return clauses.size();
    }

    public ArrayList<Integer> getClause(int n) {
        return clauses.get(n);
    }
}