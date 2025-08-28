package model.config;

import model.controller.Piece;
import model.controller.PieceColor;
import model.controller.Position;

public class Cavalo extends Piece {

    public Cavalo(PieceColor color, Position position) {
        super(color, position);

    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] tabuleiro) {
        if (newPosition.equals(this.position)) {
            return false;
        }

        int diferencaDeLinha = Math.abs(newPosition.getLinha() - position.getLinha());
        int diferencaDeColuna = Math.abs(newPosition.getColuna() - position.getColuna());

        boolean isValidMove = (diferencaDeLinha == 2 && diferencaDeColuna == 1)
                || (diferencaDeLinha == 1 && diferencaDeColuna == 2);

        if (!isValidMove) {
            return false;
        }

        Piece pecaAlvo = tabuleiro[newPosition.getLinha()][newPosition.getColuna()];
        if (pecaAlvo == null) {
            return true;
        } else {
            return pecaAlvo.getColor() != this.getColor();
        }
    }

}
