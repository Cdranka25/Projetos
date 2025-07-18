/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Classe que representa uma despesa, extendendo a classe Transacao
 * @author vinim
 */
public class Despesas extends Transacao {
    private TipoDespesa tipoDespesa;
    private String descricao;
    private TipoTransacao tipoTransacao;
    
    /**
     * Construtor da classe Despesas.
     * 
     * @param tipoDespesa o tipo de despesa
     * @param descricao a descrição da despesa
     * @param valor o valor da despesa
     * @param tipoTransacao o tipo de transação
     * @param date a data da despesa
     */
    public Despesas(TipoDespesa tipoDespesa, String descricao, double valor, 
            TipoTransacao tipoTransacao, LocalDate date) {
        super(valor, tipoTransacao, date);
        this.tipoDespesa = tipoDespesa;
        this.descricao = descricao;
        this.tipoTransacao = tipoTransacao;
    }
    /**
     * Construtor padrão da classe Despesas.
     */
    public Despesas() {
        super(0, null, null);
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoDespesa getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(TipoDespesa tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    /**
     * Cria uma nova despesa com base em um array de strings contendo os atributos.
     * 
     * @param atributos os atributos da despesa
     * @return a despesa criada
     */
    public Despesas criaDespesa(String[] atributos) {
        TipoDespesa tipoDespesaEnum = TipoDespesa.Outras_Despesas;
        TipoTransacao tipoTransacaoEnum = TipoTransacao.Despesa;
        double valor = 0;
        LocalDate data = LocalDate.now();
        
        try {
            tipoDespesaEnum = TipoDespesa.valueOf(atributos[0]);
            valor = Double.parseDouble(atributos[2]);
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy"); 
            data = LocalDate.parse(atributos[4], formato);
        } catch (Exception e) {
            System.out.println("Erro ao criar despesa: " + e.getMessage());
        }
        
        return new Despesas(tipoDespesaEnum, atributos[1], valor, tipoTransacaoEnum, data);
    }
}
