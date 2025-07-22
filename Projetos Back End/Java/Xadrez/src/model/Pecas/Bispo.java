/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Pecas;

/**
 *
 * @author cdran
 */
public class Bispo extends Peca{
    public Bispo(boolean corBranca, int linha, int coluna) {
        super("Bispo", corBranca, linha, coluna);
    }

    @Override
    public boolean movimentoValido(int novaLinha, int novaColuna, Peca[][] tabuleiro) {
        // Movimento deve ser diagonal
        int diferencaLinha = Math.abs(novaLinha - linha);
        int diferencaColuna = Math.abs(novaColuna - coluna);

        if (diferencaLinha == diferencaColuna) {
            // Verifica se o caminho está livre
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

            // O destino deve estar vazio ou conter uma peça adversária
            return tabuleiro[novaLinha][novaColuna] == null || tabuleiro[novaLinha][novaColuna].corBranca != this.corBranca;
        }

        return false; // Não é um movimento válido para o bispo
    }
}
