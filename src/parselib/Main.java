/**
 * Main
 */

package parselib;

import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;

import algorithms.*;

public class Main {
    public static void main(String[] args) {
        /*
            args:
            1 - options
            2 - file path, must end with extention ".cnf"
            3 - output file name (default is filename.out)
            
            if no options, index --, if more than one option, ++ for every option.
        */

        if(args.length == 0) {
            System.err.println("No file passed as argument. (1)");
            System.exit(10);
        }

        int index = 0;

        for(int i = 0; i < args.length; i ++) {
            if(args[i].charAt(0) != '-') {
                index = i;
                break;
            }
            options(args[i]);
        }

        // if(index == 0) {
        //     System.err.println("No file passed as argument. (2)");
        //     System.exit(10);
        // }

        String[] spl = args[index].split("\\.");
        
        if(!spl[spl.length - 1].equals("cnf")) {
            System.err.println("Wrong file type.");
            System.exit(11);
        }

        Path inPath = Paths.get(args[index]), 
            outPath;

        if(index + 1 < args.length) {
            spl = args[index + 1].split("\\.");
            if(spl[spl.length - 1] == "out")
                outPath = Paths.get(args[index + 1]);
            else 
                outPath = Paths.get(args[index + 1] + ".out");
        } else {
            spl[spl.length - 1] = "out";
            outPath = Paths.get(String.join(".", spl));
        }
        
        Lexer lexer = null;
        LookAhead1 look = null;
        Parser parser = null;
        LinkedList<int[]> clauses = new LinkedList<>();
        
        try {
            File fin = new File(inPath.toString());
            FileReader finr = new FileReader(fin);
            BufferedReader reader = new BufferedReader(finr);
            lexer = new Lexer(reader);
        } catch (Exception e) {
            System.err.println("Error while opening File.");
            e.printStackTrace();
            System.exit(1);
        }

        // try {
        //     File fout = new File(outPath.toString());
        //     FileWriter foutw = new FileWriter(fout);
        //     BufferedWriter writer = new BufferedWriter(foutw);
        // } catch (Exception e) {
        //     System.err.println("Error while creating File.");
        //     e.printStackTrace();
        //     System.exit(1);
        // }

        BufferedWriter writer;
        
        try {
            File fout = new File(outPath.toString());
            FileWriter foutw = new FileWriter(fout);
            writer = new BufferedWriter(foutw);
            look = new LookAhead1(lexer);
            parser = new Parser(look, writer);
            parser.mainLoop();
            ArrayList<ArrayList<Integer>> cl = parser.getClauses();
            
            // TODO : USE ALGORITHMS.

            writer.close();
        } catch (Exception e) {
            System.err.println("Error while opening parser.");
            e.printStackTrace();
            System.exit(1);
        }

        // try {
        //     parser.pNonTerm();
        // } catch (Exception e) {
        //     System.err.println("Error while parsing.");
        //     e.printStackTrace();
        //     System.exit(1);
        //     return;
        // }

        // writer.close();
    }

    private static Boolean verbose = false;

    private static void options(String str) {
        if(str.length() < 2)
            Main.err("Invalid option.", 12);
        for(int i = 1; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case 'v': verbose = true;
                    System.out.println("VERBOSE");
                    break;
            
                default:
                    Main.err("Invalid option : " + str.charAt(i), 13);
                    break;
            }
        }
    }

    public static void err(String comment, int exitId) {
        System.err.println(comment);
        System.exit(exitId);
    }

    /**
     * @return the verbose
     */
    public static Boolean getVerbose() {
        return verbose;
    }
}