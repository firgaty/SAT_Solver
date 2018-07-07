package util;

public abstract class ErrPrint {

    public static void err(String comment, int exitId) {
        System.err.println(comment);
        System.exit(exitId);
    }
    
}