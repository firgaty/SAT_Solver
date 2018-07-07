package util;

public class Counter {
    int val;

    public Counter() {
        this.val = 0;
    }

    public Counter(int val) {
        this.val = val;
    }

    public int get() {
        return val;
    }

    public int increment() {
        this.val++;
        return val;
    }

    public int increment(int i) {
        this.val += i;
        return val;
    }

    public int decrement() {
        this.val--;
        if (val < 0)
            
        return val;
    }

    public int decrement(int i) {
        this.val -= i;
        return val;
    }

    public void reset() {
        this.val = 0;
    }
}