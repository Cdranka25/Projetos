/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A classe representa uma conta para o programa de transações, contendo informações do titular, transações e saldo
 * @author vinim
 */
public class Conta {
    private String nomeTitular;
    private ArrayList<Transacao> transacoes = new ArrayList<>();
    private double saldo;

    
    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public ArrayList<Transacao> getTransacoes() {
        return transacoes;
    }
    
    public void addTransacao(Transacao t){
        transacoes.add(t);
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setTransacoes(ArrayList<Transacao> transacoes) {
        this.transacoes = transacoes;
    }
    
    /**
     * Calcula o saldo da conta até a data atual, considerando apenas transações 
     * anteriores à data atual.
     * 
     * @return o saldo até a data atual
     */
    public double saldoAteDataAtual() {
        double valor = 0;

        for (Transacao transacao : transacoes) {
            if (transacao instanceof Receitas && ((Receitas) transacao).getDate().isBefore(LocalDate.now())) {
                valor += ((Receitas) transacao).getValor();
            } else if (transacao instanceof Despesas && ((Despesas) transacao).getDate().isBefore(LocalDate.now())) {
                valor -= ((Despesas) transacao).getValor();
            }
        }
        return valor;
    }
    
    /**
     * Calcula o saldo total da conta, considerando todas as transações.
     * 
     * @return o saldo total
     */
    public double saldoTotal(){
        double valor = 0;
        
        for (Transacao transacao : transacoes) {
            if(transacao instanceof Receitas){
                valor+=((Receitas) transacao).getValor();  
            } else if(transacao instanceof Despesas){
                valor-=((Despesas) transacao).getValor();
            }
        }
        return valor;
    }
}
