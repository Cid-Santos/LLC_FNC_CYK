/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arquivos;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cid
 */
public class TrataArquivos {
    /*Classe responsável por manipular arquivos*/

    private static int buscarProducao(String variavel, ArrayList<Producao> gramatica) {
        /*Retorna o índice da primeira ocorrência de uma produção se encontrada 
         ou -1 caso contrário.*/
        int indice = 0;
        for (Producao producao : gramatica) {
            if (producao.getVariavel() == variavel) {
                return indice;
            }
            indice++;
        }
        return -1;
    }

    public static String trimAll(String text) {
        String string = text.trim();
        while (string.contains(" ")) {
            string = string.replaceAll(" ", "");
        }
        return string;
    }

    public static String readFile(String fileName) {
        String content = null;
        File file = new File(fileName);
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(TrataArquivos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return content;
    }

    public static ArrayList<Producao> arquivoLeitura(String file) {
        /*Método responsável pela abertura e leitura do arquivo de entrada*/
        ArrayList<Producao> gramatica = new ArrayList<>();
        List<String> lin = Arrays.asList(file.split("\r\n"));
        String linha;
        String variavel;
        List<String> regras;
        int indiceProducao;
        for (String lin1 : lin) {
            if (lin1.length() > 1) {
                //Lê a variável e as regras de uma linha do arquivo
                linha = trimAll(lin1);
                variavel = String.valueOf(linha.charAt(0));
                regras = Arrays.asList(linha.split("→")[1].split("\\|"));
                indiceProducao = buscarProducao(variavel, gramatica);
                if (indiceProducao == -1) {
                    //Caso a produção não exista, cria-se uma nova e adiciona à gramática
                    gramatica.add(new Producao(variavel, new ArrayList<>(regras)));
                } else {
                    //Caso a produção já exista, apenas adiciona novas regras a ela.
                    Producao producao = gramatica.get(indiceProducao);
                    for (String regra : regras) {
                        producao.adicionarRegra(regra);
                    }
                }
            }
        }
        return gramatica;
    }
}
