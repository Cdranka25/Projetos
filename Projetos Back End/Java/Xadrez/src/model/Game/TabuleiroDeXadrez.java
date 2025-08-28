package model.Game;

import model.config.*;
import model.controller.Piece;
import model.controller.PieceColor;
import model.controller.Position;

public class TabuleiroDeXadrez {
    private Piece[][] tabuleiro;

    public TabuleiroDeXadrez() {
        this.tabuleiro = new Piece[8][8];
        setupPieces();
    }

    public Piece[][] getTabuleiro() {
        return tabuleiro;
    }

    public Piece getPeca(int linha, int coluna) {
        return tabuleiro[linha][coluna];
    }

    public void setPeca(int linha, int coluna, Piece peca) {
        tabuleiro[linha][coluna] = peca;
        if (peca != null) {
            peca.setPosition(new Position(linha, coluna));
        }
    }

    private void setupPieces() {
        // Posições Iniciais: Torres
        tabuleiro[0][0] = new Torre(PieceColor.PRETO, new Position(0, 0));
        tabuleiro[0][7] = new Torre(PieceColor.PRETO, new Position(0, 7));
        tabuleiro[7][0] = new Torre(PieceColor.BRANCO, new Position(7, 0));
        tabuleiro[7][7] = new Torre(PieceColor.BRANCO, new Position(7, 7));
        // Posições Iniciais: Cavalos
        tabuleiro[0][1] = new Cavalo(PieceColor.PRETO, new Position(0, 1));
        tabuleiro[0][6] = new Cavalo(PieceColor.PRETO, new Position(0, 6));
        tabuleiro[7][1] = new Cavalo(PieceColor.BRANCO, new Position(7, 1));
        tabuleiro[7][6] = new Cavalo(PieceColor.BRANCO, new Position(7, 6));
        // Posições Iniciais: Bispos
        tabuleiro[0][2] = new Bispo(PieceColor.PRETO, new Position(0, 2));
        tabuleiro[0][5] = new Bispo(PieceColor.PRETO, new Position(0, 5));
        tabuleiro[7][2] = new Bispo(PieceColor.BRANCO, new Position(7, 2));
        tabuleiro[7][5] = new Bispo(PieceColor.BRANCO, new Position(7, 5));
        // Posições Iniciais: Rainhas
        tabuleiro[0][3] = new Rainha(PieceColor.PRETO, new Position(0, 3));
        tabuleiro[7][3] = new Rainha(PieceColor.BRANCO, new Position(7, 3));
        // Posições Iniciais: Reis
        tabuleiro[0][4] = new Rei(PieceColor.PRETO, new Position(0, 4));
        tabuleiro[7][4] = new Rei(PieceColor.BRANCO, new Position(7, 4));
        // Posições Iniciais: Peão
        for (int i = 0; i < 8; i++) {
            tabuleiro[1][i] = new Peao(PieceColor.PRETO, new Position(1, i));
            tabuleiro[6][i] = new Peao(PieceColor.BRANCO, new Position(6, i));
        }
    }

    public void movePiece(Position inicio, Position fim) {
        if (tabuleiro[inicio.getLinha()][inicio.getColuna()] != null
                && tabuleiro[inicio.getLinha()][inicio.getColuna()].isValidMove(fim, tabuleiro)) {

            tabuleiro[fim.getLinha()][fim.getColuna()] = tabuleiro[inicio.getLinha()][inicio.getColuna()];
            tabuleiro[fim.getLinha()][fim.getColuna()].setPosition(fim);
            tabuleiro[inicio.getLinha()][inicio.getColuna()] = null;

        }
    }

}
