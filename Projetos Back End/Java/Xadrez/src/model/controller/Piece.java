package model.controller;

public abstract class Piece {
  public Position position;
  public PieceColor color;

  public Piece(PieceColor color, Position position) {
      this.color = color;
      this.position = position;
  }

  public PieceColor getColor() {
      return color;
  }

  public Position getPosition() {
      return position;
  }

  public void setPosition(Position position) {
      this.position = position;
  }

  public abstract boolean isValidMove(Position newPosition, Piece[][] tabuleiro);
}
