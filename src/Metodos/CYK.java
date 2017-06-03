package Metodos;

import Arquivos.Producao;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Cid
 */
public class CYK {

    // Constants
    public static final int GRAMMAR_FILE = 0;
    public static final int FILE_ERROR = 1;
    public static final int LAST_ARG = 1;
    public static final int PARSE_ERROR = 2;
    public static final int TOTAL_ARGS = 2;

    /* The 2 dimensional table for the CYK algorithm */
    private static ArrayList<String>[][] table;

    /**
     * variables are in the form of (0 U 1)+ They are stored in the HashMap as
     * (0 U 1)+ maps { (0 U 1)+, (0 U 1)+ }
     */
    private HashMap<String, String[]> variables;

    /**
     * terminals are in the form of (a U b) They are stored in the hashmap in
     * the form: (0 U 1)+ maps (a U b)
     */
    private HashMap<String, Character> terminals;

    /* The start variable */
    private static String startVariable;

    /**
     * Constructs a Cyk object and initializes the HashMaps of the variables and
     * the terminals
     */
    public CYK() {
        variables = new HashMap<>();
        terminals = new HashMap<>();
    }

    /**
     * Processes the grammar file and builds the HashMap of the list of
     * terminals and variables.
     * @param file the string representing the path of the grammar file
     */
    public void processGrammarFile(String file) {
        File grammarFile = null;
        Scanner scanner = null;
        try {
            grammarFile = new File(file);
            scanner = new Scanner(grammarFile);
            String[] line = scanner.nextLine().split("→");
            startVariable = line[0];
            do {
                String variable = line[0];
                if ((line[1].equals("a") || line[1].equals("b"))) {
                    terminals.put(variable, line[1].charAt(0));
                } else {
                    String[] rest = line[1].split(",");
                    if (rest != null) {
                        variables.put(variable, rest);
                    }
                }
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().split("→");
                } else {
                    line = null;
                }
            } while (line != null);
            scanner.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Tests the string against the given grammar file using the CYK Algorithm.
     * In the current version, warnings about type safety, are suppressed
     *
     * @param w the input string to test
     * @return true if string w is accepted by the grammar, false otherwise.
     */
    @SuppressWarnings("unchecked")
    public boolean processString(String w) {
        int length = w.length();
        table = new ArrayList[length][];
        for (int i = 0; i < length; ++i) {
            table[i] = new ArrayList[length];
            for (int j = 0; j < length; ++j) {
                table[i][j] = new ArrayList<>();
            }
        }
        for (int i = 0; i < length; ++i) {
            Set<String> keys = terminals.keySet();
            for (String key : keys) {
                if (terminals.get(key).charValue() == w.charAt(i)) {
                    table[i][i].add(key);
                }
            }
        }
        for (int l = 2; l <= length; ++l) {
            for (int i = 0; i <= length - l; ++i) {
                int j = i + l - 1;
                for (int k = i; k <= j - 1; ++k) {
                    Set<String> keys = variables.keySet();
                    for (String key : keys) {
                        String[] values = variables.get(key);
                        if (table[i][k].contains((values[0]))
                                && table[k + 1][j].contains(values[1])) {
                            table[i][j].add(key);
                        }
                    }
                }
            }
        }
        if (table[0][length - 1].contains(startVariable)) // we started from 0
        {
            return true;
        }
        return false;
    }
}
