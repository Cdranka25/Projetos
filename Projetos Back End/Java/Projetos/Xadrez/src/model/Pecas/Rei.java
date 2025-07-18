/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Pecas;

/**
 *
 * @author cdran
 */
public class Rei extends Peca {

public Rei(boolean corBranca, int linha, int coluna) {
        super("Rei", corBranca, linha, coluna);
    }

    @Override
    public boolean movimentoValido(int novaLinha, int novaColuna, Peca[][] tabuleiro) {
        // Calcula a diferença entre a posição atual e a nova posição
        int diferencaLinha = Math.abs(novaLinha - linha);
        int diferencaColuna = Math.abs(novaColuna - coluna);

        // O Rei pode se mover uma casa em qualquer direção
        if ((diferencaLinha <= 1 && diferencaColuna <= 1) && (diferencaLinha + diferencaColuna > 0)) {
            // Verifica se o destino está vazio ou contém uma peça adversária
            if (tabuleiro[novaLinha][novaColuna] == null || tabuleiro[novaLinha][novaColuna].corBranca != this.corBranca) {
                return true;
            }
        }

        return false;
    }
}