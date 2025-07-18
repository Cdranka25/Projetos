/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Pecas;

/**
 *
 * @author cdran
 */
public class Rainha extends Peca{
    public Rainha(boolean corBranca, int linha, int coluna) {
        super("Rainha", corBranca, linha, coluna);
    }

    @Override
    public boolean movimentoValido(int novaLinha, int novaColuna, Peca[][] tabuleiro) {
        // Combina os movimentos do bispo e da torre
        int diferencaLinha = Math.abs(novaLinha - linha);
        int diferencaColuna = Math.abs(novaColuna - coluna);

        // Movimento como bispo
        if (diferencaLinha == diferencaColuna) {
            int incrementoLinha = Integer.compare(novaLinha, linha);
            int incrementoColuna = Integer.compare(novaColuna, coluna);

            int posicaoLinha = linha + incrementoLinha;
            int posicaoColuna = coluna + incrementoColuna;

            while (posicaoLinha != novaLinha && posicaoColuna != novaColuna) {
                if (tabuleiro[posicaoLinha][posicaoColuna] != null) {
                    return false; // Obstáculo no caminho
                }
                posicaoLinha += incrementoLinha;
                posicaoColuna += incrementoColuna;
            }

            return tabuleiro[novaLinha][novaColuna] == null || tabuleiro[novaLinha][novaColuna].corBranca != this.corBranca;
        }

        // Movimento como torre
        if (linha == novaLinha || coluna == novaColuna) {
            int incrementoLinha = Integer.compare(novaLinha, linha);
            int incrementoColuna = Integer.compare(novaColuna, coluna);

            int posicaoLinha = linha + incrementoLinha;
            int posicaoColuna = coluna + incrementoColuna;

            while (posicaoLinha != novaLinha || posicaoColuna != novaColuna) {
                if (tabuleiro[posicaoLinha][posicaoColuna] != null) {
                    return false; // Obstáculo no caminho
                }
                posicaoLinha += incrementoLinha;
                posicaoColuna += incrementoColuna;
            }

            return tabuleiro[novaLinha][novaColuna] == null || tabuleiro[novaLinha][novaColuna].corBranca != this.corBranca;
        }

        return false; // Não é um movimento válido para a rainha
    }
}
