package model.config;

import model.controller.Piece;
import model.controller.PieceColor;
import model.controller.Position;

public class Rei extends Piece {

    public Rei(PieceColor color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] tabuleiro) {
        int diferencaDeLinha = Math.abs(position.getLinha() - newPosition.getLinha());
        int diferencaDeColuna = Math.abs(position.getColuna() - newPosition.getColuna());

        boolean movimentoValido = diferencaDeLinha <= 1 && diferencaDeColuna <= 1
                && !(diferencaDeLinha == 0 && diferencaDeColuna == 0);

        if (!movimentoValido) {
            return false;
        }

        Piece pecaDestino = tabuleiro[newPosition.getLinha()][newPosition.getColuna()];
        return pecaDestino == null || pecaDestino.getColor() != this.getColor();
    }
}