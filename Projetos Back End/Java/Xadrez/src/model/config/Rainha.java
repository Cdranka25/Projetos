package model.config;

import model.controller.Piece;
import model.controller.PieceColor;
import model.controller.Position;

public class Rainha extends Piece {

    public Rainha(PieceColor color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] tabuleiro) {
        if (newPosition.equals(this)) {
            return false;
        }
        int diferencaDeLinha = Math.abs(newPosition.getLinha() - newPosition.getLinha());
        int diferencaDeColuna = Math.abs(newPosition.getColuna() - newPosition.getColuna());

        boolean linhaReta = this.position.getLinha() == newPosition.getLinha()
                || this.position.getColuna() == newPosition.getColuna();
        boolean diagonal = diferencaDeLinha == diferencaDeColuna;

        if (!linhaReta && !diagonal) {
            return false;
        }

        int direcaoLinha = Integer.compare(newPosition.getLinha(), this.position.getLinha());
        int direcaoColuna = Integer.compare(newPosition.getColuna(), this.position.getColuna());
        int linhaAtual = this.position.getLinha() + direcaoLinha;
        int colunaAtual = this.position.getColuna() + direcaoColuna;

        while (linhaAtual != newPosition.getLinha() || colunaAtual != newPosition.getColuna()) {
            if (tabuleiro[linhaAtual][colunaAtual] != null) {
                return false;
            }
            linhaAtual += direcaoLinha;
            colunaAtual += direcaoColuna;
        }

        Piece pecaDestino = tabuleiro[newPosition.getLinha()][newPosition.getColuna()];
        return pecaDestino == null || pecaDestino.getColor() != this.getColor();
    }

}
