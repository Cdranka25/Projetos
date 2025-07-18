/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Classe responsável por importar transações de um arquivo.
 * @author vinim
 */

public class ImportadorArquivo {

    private ArrayList<Transacao> transacoes;
    private File arquivo = new File("bin\\bin.csv");

    /**
     * Construtor da classe ImportadorArquivo.
     */
    public ImportadorArquivo() {
        transacoes = new ArrayList<>();
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }

    public ArrayList<Transacao> getTransacoes() {
        return transacoes;
    }

    /**
     * Processa o arquivo de importação, lendo e criando as transações.
     */
    public void processarArquivo() {
        try (BufferedReader bf = new BufferedReader(new FileReader(arquivo))) {
            String line = bf.readLine(); 
            while ((line = bf.readLine()) != null) {
                String[] atributos = line.split(";");

                if (atributos.length == 5) { 
                    String tipoTransacao = atributos[3];
                    if (tipoTransacao.equals("Receita")) {
                        Receitas receita = new Receitas().criaReceita(atributos);
                        transacoes.add(receita);
                    } else if (tipoTransacao.equals("Despesa")) {
                        Despesas despesa = new Despesas().criaDespesa(atributos);
                        transacoes.add(despesa);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ImportadorArquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object getArquivo() {
        return arquivo;
    }
}