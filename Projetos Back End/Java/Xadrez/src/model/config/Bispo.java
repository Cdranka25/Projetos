package model.config;

import model.controller.Piece;
import model.controller.PieceColor;
import model.controller.Position;

public class Bispo extends Piece {

    public Bispo(PieceColor color, Position position) {
        super(color, position);
        
    }

    //Bispo se move na diagonal em qualquer quantia de casas
    @Override
    public boolean isValidMove(Position newPosition, Piece[][] tabuleiro) {
        int diferencaDeLinha = Math.abs(position.getLinha() - newPosition.getLinha());
        int diferencaDeColuna = Math.abs(position.getColuna() - newPosition.getColuna());

        if(diferencaDeLinha != diferencaDeColuna) {
            return false;
        }

        int linhaAux = newPosition.getLinha() > position.getLinha() ? 1 : -1;
        int colunaAux = newPosition.getColuna() > position.getColuna() ? 1 : -1;
        int aux = diferencaDeLinha - 1;

        for (int i = 1; i <= aux; i++) {
            if(tabuleiro[position.getLinha() + i * linhaAux][position.getColuna() + i * colunaAux] != null) {
                return false;
            }
        }

        Piece destinoPeca = tabuleiro[newPosition.getLinha()][newPosition.getColuna()];;
        if(destinoPeca == null) {
            return true;
        } else if(destinoPeca.getColor() != this.getColor()) {
            return true;
        } else if (destinoPeca.getColor() !=this.getColor()) {
            return true;
        }
        return  false;
    }
}
