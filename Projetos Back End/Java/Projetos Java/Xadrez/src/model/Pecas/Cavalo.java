/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Pecas;

/**
 *
 * @author cdran
 */
public class Cavalo extends Peca {
    
    public Cavalo(boolean corBranca, int linha, int coluna) {
        super("Cavalo", corBranca, linha, coluna);
    }

    @Override
    public boolean movimentoValido(int novaLinha, int novaColuna, Peca[][] tabuleiro) {
        // Movimento em "L"
        int diferencaLinha = Math.abs(novaLinha - linha);
        int diferencaColuna = Math.abs(novaColuna - coluna);

        if ((diferencaLinha == 2 && diferencaColuna == 1) || (diferencaLinha == 1 && diferencaColuna == 2)) {
            // O destino deve estar vazio ou conter uma peça adversária
            return tabuleiro[novaLinha][novaColuna] == null || tabuleiro[novaLinha][novaColuna].corBranca != this.corBranca;
        }

        return false;
    }
}