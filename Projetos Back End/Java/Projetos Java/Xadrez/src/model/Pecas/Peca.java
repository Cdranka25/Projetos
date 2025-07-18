/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Pecas;

/**
 *
 * @author cdran
 */
public abstract class Peca {

    protected String nome;
    protected boolean corBranca; // true para casa branca e false para casa preta
    protected int linha, coluna;

    public Peca(String nome, boolean corBranca, int linha, int coluna) {
        this.nome = nome;
        this.corBranca = corBranca;
        this.linha = linha;
        this.coluna = coluna;
    }

    public abstract boolean movimentoValido(int novaLinha, int novaColuna, Peca[][] tabuleiro);

    public String getNome() {
        return nome;
    }
}
