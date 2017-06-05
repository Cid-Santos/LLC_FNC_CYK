/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

/**
 *
 * @author ti1
 */
public class Rule {
    
    public char left;
    public String right;
    
    public Rule(char l, String r) {
        this.left = l;
        this.right = r;
    }
    
    public String ToFineString() {
        String s = left + " -->";
        for (int i = 0; i < right.length(); i++) {
            s += " " + right.charAt(i);
        }
        return s;
    }    
    
}
