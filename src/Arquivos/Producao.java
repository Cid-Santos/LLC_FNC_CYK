/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arquivos;

import java.util.ArrayList;

/**
 * Classe que representa uma produção de regras
 *
 * @author Cid
 */
public class Producao {

    private String variavel;
    private ArrayList<String> regras;


    //Construtor da classe
    public Producao(String variavel, ArrayList<String> regras) {
        this.variavel = variavel;
        this.regras = (ArrayList<String>) regras.clone();
    }

    //Adiciona uma nova regra a uma produção
    public void adicionarRegra(String regra) {
        if (getRegras().indexOf(regra) == -1) {
            getRegras().add(regra);
        }
    }

    /**
     * @return the variavel
     */
    public String getVariavel() {
        return variavel;
    }

    /**
     * @param variavel the variavel to set
     */
    public void setVariavel(String variavel) {
        this.variavel = variavel;
    }

    /**
     * @return the regras
     */
    public ArrayList<String> getRegras() {
        return regras;
    }

    /**
     * @param regras the regras to set
     */
    public void setRegras(ArrayList<String> regras) {
        this.regras = regras;
    }
}
