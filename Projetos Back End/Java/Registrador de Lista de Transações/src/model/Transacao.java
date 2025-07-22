/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
//import java.time.LocalTime;

/**
 * Classe que representa uma transação financeira.
 * @author vinim
 */
public class Transacao {

    private double valor;
    private TipoTransacao tipoTransacao;
    private LocalDate date;
    
    /**
     * Construtor da classe Transacao.
     * 
     * @param valor o valor da transação
     * @param tipoTransacao o tipo de transação
     * @param date a data da transação
     */
    public Transacao(double valor, TipoTransacao tipoTransacao, LocalDate date) {
        this.valor = valor;
        this.tipoTransacao = tipoTransacao;
        this.date = date;
        //this.time = time;
    }
    /**
     * Construtor padrão da classe Transacao.
     */
    public Transacao() {
    }

    public double getValor() {
        return valor;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public LocalDate getDate() {
        return date;
    }

    /*public LocalTime getTime() {
        return time;
    }*/
    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    /**
     * Retorna uma representação em string da transação.
     * 
     * @return uma string representando a transação
     */
    public String toString() {
        return "Transacao{"
                + "valor=" + valor
                + ", tipoTransacao=" + tipoTransacao
                + ", date=" + date
                + '}';
    }

}
