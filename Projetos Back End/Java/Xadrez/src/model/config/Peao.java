package model.config;

import model.controller.Piece;
import model.controller.PieceColor;
import model.controller.Position;

public class Peao extends Piece {
    public Peao(PieceColor color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] tabuleiro) {
        int avancar = color == PieceColor.BRANCO ? -1 : 1;
        int diferencaDeLinha = (newPosition.getLinha() - position.getLinha()) * avancar;
        int diferencaDeColuna = newPosition.getColuna() - position.getColuna();

        // Movimento para frente, avança um quadrado
        if (diferencaDeColuna == 0 && diferencaDeLinha == 1
                && tabuleiro[newPosition.getLinha()][newPosition.getColuna()] == null) {
            return true;
        }

        // Movimento inicial, permite avançar dois quadrados
        boolean isStartingPosition = (color == PieceColor.BRANCO && position.getLinha() == 6) ||
                (color == PieceColor.PRETO && position.getLinha() == 1);
        if (diferencaDeColuna == 0 && diferencaDeLinha == 2 && isStartingPosition
                && tabuleiro[newPosition.getLinha()][newPosition.getColuna()] == null) {
            int linhaDoMeio = position.getLinha() + avancar;
            if (tabuleiro[linhaDoMeio][newPosition.getColuna()] == null) {
                return true;
            }
        }

        // Captura de peça na diagonal
        if (Math.abs(diferencaDeColuna) == 1 && diferencaDeLinha == 1 &&
                tabuleiro[newPosition.getLinha()][newPosition.getColuna()] != null &&
                tabuleiro[newPosition.getLinha()][newPosition.getColuna()].color != this.color) {
            return true;
        }

        return false;

    }
}
