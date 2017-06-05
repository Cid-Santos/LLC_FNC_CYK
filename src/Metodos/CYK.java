package Metodos;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

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

    public List<Rule> rules = new ArrayList<>();
    public String[][] table = null;
    public String w;
    int n = 0;

    public void AddRule(char left, String right) {
        Rule rule = new Rule(left, right);
        rules.add(rule);
    }

    public String printAllRules() {
        String s = "";
        s = rules.stream().map((rule) -> rule.ToFineString()).reduce(s, String::concat);
        return s;
    }

    public void initData(String x) {
        w = x;
        n = x.length();
        table = new String[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                table[i][j] = "";
            }
        }
    }

    public void fristProcess() {
        for (int i = 1; i <= n; i++) {
            for (Rule rule : rules) {
                if (rule.right.length() == 1) {
                    if (w.charAt(i - 1) == rule.right.charAt(0)) {
                        table[i][1] += rule.left;
                    }
                }
            }
        }
    }

    public boolean isIn(char x, String w) {
        return w.indexOf(x) != -1;
    }

    public boolean canGen(int j, int i, Rule rule) {
        for (int k = 1; k <= j - 1; k++) {
            if (isIn(rule.right.charAt(0), table[i][k])) {
                if (isIn(rule.right.charAt(1), table[i + k][j - k])) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean genTable() {
        boolean valida = false;
        for (int j = 2; j <= n; j++) {
            for (int i = 1; i <= n - j + 1; i++) {
                for (Rule rule : rules) {
                    if (rule.right.length() == 2) {
                        if (!isIn(rule.left, table[i][j])) {
                            if (canGen(j, i, rule)) {
                                table[i][j] += rule.left;
                                valida = true;
                            }
                        }
                    }
                }
            }
        }
        return valida;
    }

    public void process(String x) {
        initData(x);
        fristProcess();
       if(genTable()){
          JOptionPane.showMessageDialog(null, "Sentença é valida!");
       }else
       {
          JOptionPane.showMessageDialog(null, "Sentença não é valida!!");
       }
    }

    public String printResult() {
        String s = "";
        String result = "";
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                s += String.format("[%-2s]", table[j][n + 1 - i]);
            }
            s += "\n";
        }
        return s;
    }

}
