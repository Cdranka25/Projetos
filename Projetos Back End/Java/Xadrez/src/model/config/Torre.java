package model.config;

import model.controller.Piece;
import model.controller.PieceColor;
import model.controller.Position;

public class Torre extends Piece {
    
    public Torre(PieceColor color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] tabuleiro) {
        // Torre pode se mover verticalmente e horizontalmente em qualquer quantidade de quadrados, mas não pode pular peças
        if (position.getLinha() == newPosition.getLinha()) {
            
            int inicioColuna = Math.min(position.getColuna(), newPosition.getColuna()) + 1;
            
            int fimColuna = Math.max(position.getColuna(), newPosition.getColuna());

            for(int column = inicioColuna; column < fimColuna; column++) {
                if(tabuleiro[position.getLinha()][column] != null) {
                    return false;
                }
            }
        } else if(position.getColuna() == newPosition.getColuna()) {
            int inicioLinha = Math.min(position.getLinha(), newPosition.getLinha()) + 1;
            int fimLinha = Math.max(position.getLinha(), newPosition.getLinha());

            for(int row = inicioLinha; row < fimLinha; row++) {
                if(tabuleiro[row][position.getColuna()] != null) {
                    return false;
                }
            } 
        } else {
            return false;
        }

        Piece destinoPiece = tabuleiro[newPosition.getLinha()][newPosition.getColuna()];

        if(destinoPiece == null) {
            return true;
        } else if(destinoPiece.getColor() != this.getColor()) {
            return true;
        }
        return false;

    }
    
}
