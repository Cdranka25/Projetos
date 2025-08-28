package model.controller;

public class Position {
    private int linha;
    private int coluna;

    public Position(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }
    
}
