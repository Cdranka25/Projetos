/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 *Classe que representa uma receita, extendendo a classe Transacao.
 * @author vinim
 */
public class Receitas extends Transacao {
    private TipoReceita tipoReceita;
    private String descricao;
    private TipoTransacao tipoTransacao;
    
    /**
     * Construtor da classe Receitas.
     * 
     * @param tipoReceita o tipo de receita
     * @param descricao a descrição da receita
     * @param valor o valor da receita
     * @param tipoTransacao o tipo de transação
     * @param date a data da receita
     */
    public Receitas(TipoReceita tipoReceita, String descricao, double valor,
            TipoTransacao tipoTransacao, LocalDate date) {
        super(valor, tipoTransacao, date);
        this.tipoReceita = tipoReceita;
        this.descricao = descricao;
        this.tipoTransacao = tipoTransacao;
    }
    
    /**
     * Construtor padrão da classe Receitas.
     */
    public Receitas() {
        super(0, null, null);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoReceita getTipoReceita() {
        return tipoReceita;
    }

    public void setTipoReceita(TipoReceita tipoReceita) {
        this.tipoReceita = tipoReceita;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    /**
     * Cria uma nova receita com base em um array de strings contendo os atributos.
     * 
     * @param atributos os atributos da receita
     * @return a receita criada
     */
    public Receitas criaReceita(String[] atributos) {
        TipoReceita tipoReceitaEnum = TipoReceita.Outras_Receitas;
        TipoTransacao tipoTransacaoEnum = TipoTransacao.Receita;
        double valor = 0;
        LocalDate data = LocalDate.now();
        
        try {
            tipoReceitaEnum = TipoReceita.valueOf(atributos[0]);
            valor = Double.parseDouble(atributos[2]);
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy"); 
            data = LocalDate.parse(atributos[4], formato);
        } catch (Exception e) {
            System.out.println("Erro ao criar receita: " + e.getMessage());
        }
        
        return new Receitas(tipoReceitaEnum, atributos[1], valor, tipoTransacaoEnum, data);
    }
}
