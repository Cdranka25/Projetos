/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Pecas;

/**
 *
 * @author cdran
 */
public class Peao extends Peca{

    public Peao(boolean corBranca, int linha, int coluna) {
        super("Peão", corBranca, linha, coluna);
    }

    @Override
    public boolean movimentoValido(int novaLinha, int novaColuna, Peca[][] tabuleiro) {
        
        int direcao = corBranca ? 1 : -1; // Branco vai para frente, preto vai para trás
        if(novaColuna == coluna && novaLinha == linha + direcao && tabuleiro[novaLinha][novaColuna] == null) {
            return true; // movimento simples. Peão avança uma casa
        }
        
        //ataque na diagonal
        if(Math.abs(novaColuna - coluna) == 1 && novaLinha == linha + direcao && tabuleiro[novaLinha][novaColuna] != null) {
            return true;
        }
        return false;
    } 
}
