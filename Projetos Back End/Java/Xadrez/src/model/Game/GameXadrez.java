package model.Game;

import java.util.ArrayList;
import java.util.List;

import model.config.*;
import model.controller.*;

public class GameXadrez {
    private TabuleiroDeXadrez tabuleiro;
    private boolean turnoPecasBrancas = true;

    public GameXadrez() {
        tabuleiro = new TabuleiroDeXadrez();
    }

    public boolean fazerMovimento(Position inicio, Position fim) {
        Piece movendoPeca = tabuleiro.getPeca(inicio.getLinha(), inicio.getColuna());
        if (movendoPeca == null
                || movendoPeca.getColor() != (turnoPecasBrancas ? PieceColor.BRANCO : PieceColor.PRETO)) {
            return false;
        }
        if (movendoPeca.isValidMove(fim, tabuleiro.getTabuleiro())) {
            tabuleiro.movePiece(inicio, fim);
            turnoPecasBrancas = !turnoPecasBrancas;
            return true;
        }
        return false;
    }

    private Position encontrarRei(PieceColor color) {
        for (int linha = 0; linha < tabuleiro.getTabuleiro().length; linha++) {
            for (int coluna = 0; coluna < tabuleiro.getTabuleiro()[linha].length; coluna++) {
                Piece peca = tabuleiro.getPeca(linha, coluna);
                if (peca instanceof Rei && peca.getColor() == color) {
                    return new Position(linha, coluna);
                }
            }
        }
        throw new RuntimeException("Rei não encontrado");
    }

    public boolean esta_Em_Cheque(PieceColor corRei) {
        Position posicaoRei = encontrarRei(corRei);
        for (int linha = 0; linha < tabuleiro.getTabuleiro()[linha].length; linha++) {
            for (int coluna = 0; coluna < tabuleiro.getTabuleiro()[linha].length; coluna++) {
                Piece peca = tabuleiro.getPeca(linha, coluna);
                if (peca != null && peca.getColor() != corRei) {
                    if (peca.isValidMove(posicaoRei, tabuleiro.getTabuleiro())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean esta_em_ChquekMate(PieceColor corRei) {
        if (!esta_Em_Cheque(corRei)) {
            return false;
        }
        Position posicaoRei = encontrarRei(corRei);
        Rei rei = (Rei) tabuleiro.getPeca(posicaoRei.getLinha(), posicaoRei.getColuna());

        for (int deslocamentoDeLinha = -1; deslocamentoDeLinha <= 1; deslocamentoDeLinha++) {
            for (int deslocamentoDeColuna = -1; deslocamentoDeColuna <= 1; deslocamentoDeColuna++) {
                if (deslocamentoDeLinha == 0 && deslocamentoDeColuna == 0) {
                    continue;
                }
                Position newPosition = new Position(posicaoRei.getLinha() + deslocamentoDeLinha,
                        posicaoRei.getColuna() + deslocamentoDeColuna);
                if (posicaoDentroDoTabuleiro(newPosition) && rei.isValidMove(newPosition, tabuleiro.getTabuleiro())
                        && !estaEmCheque_AposMovimento(corRei, posicaoRei, newPosition)) {

                    return false;
                }
            }
        }
        return true;
    }

    private boolean posicaoDentroDoTabuleiro(Position position) {
        return position.getLinha() >= 0 && position.getLinha() < tabuleiro.getTabuleiro().length
                && position.getColuna() >= 0 && position.getColuna() < tabuleiro.getTabuleiro()[0].length;
    }

    private boolean estaEmCheque_AposMovimento(PieceColor corRei, Position inicio, Position fim) {
        Piece aux = tabuleiro.getPeca(fim.getLinha(), fim.getColuna());

        tabuleiro.setPeca(fim.getLinha(), fim.getColuna(), tabuleiro.getPeca(inicio.getLinha(), inicio.getColuna()));
        tabuleiro.setPeca(inicio.getLinha(), inicio.getColuna(), null);

        boolean emCheque = esta_Em_Cheque(corRei);

        tabuleiro.setPeca(inicio.getLinha(), inicio.getColuna(), tabuleiro.getPeca(fim.getLinha(), fim.getColuna()));
        tabuleiro.setPeca(fim.getLinha(), fim.getColuna(), aux);

        return emCheque;

    }

    public TabuleiroDeXadrez getTabuleiro() {
        return this.tabuleiro;
    }

    public void resetGame() {
        this.tabuleiro = new TabuleiroDeXadrez();
        this.turnoPecasBrancas = true;
    }

    public PieceColor getJogadorAtualColor() {
        return turnoPecasBrancas ? PieceColor.BRANCO : PieceColor.PRETO;
    }

    private Position selecionarPosicao;

    public boolean pecaEstaSelecionada() {
        return selecionarPosicao != null;
    }

    public boolean tratarSelecaoDeQuadrado(int linha, int coluna) {
        if (selecionarPosicao == null) {
            Piece pecaSelecionada = tabuleiro.getPeca(linha, coluna);
            if (pecaSelecionada != null
                    && pecaSelecionada.getColor() == (turnoPecasBrancas ? PieceColor.BRANCO : PieceColor.PRETO)) {
                selecionarPosicao = new Position(linha, coluna);
                return false;
            }
        } else {
            boolean movimentoRealizado = fazerMovimento(selecionarPosicao, new Position(linha, coluna));
            selecionarPosicao = null;
            return movimentoRealizado;
        }
        return false;
    }

    public List<Position> getMovimentosPossiveisParaPeca(Position position) {
        Piece pecaSelecionada = tabuleiro.getPeca(position.getLinha(), position.getColuna());

        if (pecaSelecionada == null) {
            return new ArrayList<>();
        }
        List<Position> movimentosPossiveis = new ArrayList<>();
        switch (pecaSelecionada.getClass().getSimpleName()) {
            case "Peao":
                addPeaoMovimentos(position, pecaSelecionada.getColor(), movimentosPossiveis);
                break;
            case "Torre":
                addMovimetosDeLinha(position, new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } },
                        movimentosPossiveis);
                break;
            case "Cavalo":
                addMovimentosUnicos(position, new int[][] { { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 }, { 1, 2 },
                        { -1, 2 }, { 1, -2 }, { -1, -2 } }, movimentosPossiveis);
                break;
            case "Bispo":
                addMovimetosDeLinha(position, new int[][] { { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 } },
                        movimentosPossiveis);
                break;
            case "Rainha":
                addMovimetosDeLinha(position, new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 },
                        { -1, -1 }, { 1, -1 }, { -1, 1 } }, movimentosPossiveis);
                break;
            case "Rei":
                addMovimentosUnicos(position,
                        new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { -1, -1 },
                                { 1, -1 }, { -1, 1 } },
                        movimentosPossiveis);
                break;
        }
        return movimentosPossiveis;
    }

    private void addMovimetosDeLinha(Position position, int[][] directions, List<Position> movimentosPossiveis) {
        for (int[] d : directions) {
            Position newPosition = new Position(position.getLinha() + d[0], position.getColuna() + d[1]);
            while (posicaoDentroDoTabuleiro(newPosition)) {
                if (tabuleiro.getPeca(newPosition.getLinha(), newPosition.getColuna()) == null) {
                    movimentosPossiveis.add(new Position(newPosition.getLinha(), newPosition.getColuna()));
                    newPosition = new Position(newPosition.getLinha() + d[0], newPosition.getColuna() + d[1]);
                } else {
                    if (tabuleiro.getPeca(newPosition.getLinha(), newPosition.getColuna()).getColor() != tabuleiro
                            .getPeca(position.getLinha(), position.getColuna()).getColor()) {
                        movimentosPossiveis.add(newPosition);
                    }
                    break;
                }
            }
        }
    }

    private void addMovimentosUnicos(Position position, int[][] moves, List<Position> movimentosPossiveis) {
        for (int[] movimento : moves) {
            Position newPosition = new Position(position.getLinha() + movimento[0],
                    position.getColuna() + movimento[1]);
            if (posicaoDentroDoTabuleiro(newPosition)
                    && (tabuleiro.getPeca(newPosition.getLinha(), newPosition.getColuna()) == null || tabuleiro
                            .getPeca(newPosition.getLinha(), newPosition.getColuna())
                            .getColor() != tabuleiro.getPeca(position.getLinha(), position.getColuna()).getColor())) {
                movimentosPossiveis.add(newPosition);

            }
        }
    }

    private void addPeaoMovimentos(Position position, PieceColor color, List<Position> movimentosPossiveis) {
        int direcao = color == PieceColor.BRANCO ? -1 : 1;
        Position newPos = new Position(position.getLinha() + direcao, position.getColuna());
        if (posicaoDentroDoTabuleiro(newPos) && tabuleiro.getPeca(newPos.getLinha(), newPos.getColuna()) == null) {
            movimentosPossiveis.add(newPos);
        }
        if ((color == PieceColor.BRANCO && position.getLinha() == 6)
                || (color == PieceColor.PRETO && position.getLinha() == 1)) {
            newPos = new Position(position.getLinha() + 2 * direcao, position.getColuna());
            Position intermediarioPos = new Position(position.getLinha() + direcao, position.getColuna());

            if (posicaoDentroDoTabuleiro(newPos) && tabuleiro.getPeca(newPos.getLinha(), newPos.getColuna()) == null
                    && tabuleiro.getPeca(intermediarioPos.getLinha(), intermediarioPos.getColuna()) == null) {
                movimentosPossiveis.add(newPos);
            }
        }

        int[] capture = { position.getColuna() - 1, position.getColuna() + 1 };
        for (int col : capture) {
            newPos = new Position(position.getLinha() + direcao, col);
            if (posicaoDentroDoTabuleiro(newPos) && tabuleiro.getPeca(newPos.getLinha(), newPos.getColuna()) != null
                    && tabuleiro.getPeca(newPos.getLinha(), newPos.getColuna()).getColor() != color) {
                movimentosPossiveis.add(newPos);
            }
        }
    }
}
