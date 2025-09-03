package model.Game;

import java.util.ArrayList;
import java.util.List;

import model.config.*;
import model.controller.*;

public class GameXadrez {
    private static final int N = 8;

    private TabuleiroDeXadrez tabuleiro;
    private boolean turnoPecasBrancas = true;

    public GameXadrez() {
        this.tabuleiro = new TabuleiroDeXadrez();
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

    // ==================== MOVIMENTAÇÃO ====================

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

    // ==================== REI / CHEQUE / CHEQUE-MATE ====================

    private Position encontrarReiOuNull(PieceColor color) {
        for (int linha = 0; linha < N; linha++) {
            for (int coluna = 0; coluna < N; coluna++) {
                Piece peca = tabuleiro.getPeca(linha, coluna);
                if (peca instanceof Rei && peca.getColor() == color) {
                    return new Position(linha, coluna);
                }
            }
        }
        return null;
    }

    public boolean esta_Em_Cheque(PieceColor corRei) {
        Position posicaoRei = encontrarReiOuNull(corRei);
        if (posicaoRei == null) {
            return true;
        }
        PieceColor atacante = (corRei == PieceColor.BRANCO) ? PieceColor.PRETO : PieceColor.BRANCO;
        return casaEstaAtacadaPor(posicaoRei, atacante);
    }

    public boolean esta_em_ChequekMate(PieceColor corRei) {
        Position posicaoRei = encontrarReiOuNull(corRei);
        if (posicaoRei == null) return true;

        if (!esta_Em_Cheque(corRei)) return false;

        for (int linha = 0; linha < N; linha++) {
            for (int coluna = 0; coluna < N; coluna++) {
                Piece p = tabuleiro.getPeca(linha, coluna);
                if (p != null && p.getColor() == corRei) {
                    Position origem = new Position(linha, coluna);
                    for (Position destino : getPseudoMovimentosParaPeca(origem)) {
                       
                        boolean salva = simularMovimentoMantendoEstado(origem, destino, () -> !esta_Em_Cheque(corRei));
                        if (salva) return false;
                    }
                }
            }
        }
        return true;
    }

    // ==================== SELEÇÃO NA GUI ====================

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

    // ==================== MOVIMENTOS: LEGAIS (filtrados) ====================

    public List<Position> getMovimentosPossiveisParaPeca(Position position) {
        Piece p = tabuleiro.getPeca(position.getLinha(), position.getColuna());
        if (p == null) return new ArrayList<>();

        List<Position> candidatos = getPseudoMovimentosParaPeca(position);

        List<Position> legais = new ArrayList<>();
        for (Position destino : candidatos) {
            if (simularMovimentoMantendoEstado(position, destino, () -> !esta_Em_Cheque(p.getColor()))) {
                legais.add(destino);
            }
        }
        return legais;
    }

    // ==================== PSEUDO MOVIMENTOS (sem considerar cheque) ====================

    public List<Position> getPseudoMovimentosParaPeca(Position position) {
        Piece p = tabuleiro.getPeca(position.getLinha(), position.getColuna());
        List<Position> movimentos = new ArrayList<>();
        if (p == null) return movimentos;

        String nome = p.getClass().getSimpleName(); 
        switch (nome) {
            case "Peao":
                addPeaoMovimentos(position, p.getColor(), movimentos);
                break;
            case "Torre":
                addMovimetosDeLinha(position, new int[][]{{1,0},{-1,0},{0,1},{0,-1}}, movimentos);
                break;
            case "Cavalo":
                addMovimentosUnicos(position, new int[][]{{2,1},{2,-1},{-2,1},{-2,-1},{1,2},{-1,2},{1,-2},{-1,-2}}, movimentos);
                break;
            case "Bispo":
                addMovimetosDeLinha(position, new int[][]{{1,1},{-1,-1},{1,-1},{-1,1}}, movimentos);
                break;
            case "Rainha":
                addMovimetosDeLinha(position, new int[][]{{1,0},{-1,0},{0,1},{0,-1},{1,1},{-1,-1},{1,-1},{-1,1}}, movimentos);
                break;
            case "Rei":
                addMovimentosUnicos(position, new int[][]{{1,0},{-1,0},{0,1},{0,-1},{1,1},{-1,-1},{1,-1},{-1,1}}, movimentos);
                break;
        }
        return movimentos;
    }

    private void addMovimetosDeLinha(Position position, int[][] directions, List<Position> movimentos) {
        Piece origem = tabuleiro.getPeca(position.getLinha(), position.getColuna());
        for (int[] d : directions) {
            int r = position.getLinha() + d[0];
            int c = position.getColuna() + d[1];
            while (posicaoDentroDoTabuleiro(r, c)) {
                Piece alvo = tabuleiro.getPeca(r, c);
                if (alvo == null) {
                    movimentos.add(new Position(r, c));
                } else {
                    if (alvo.getColor() != origem.getColor()) {
                        movimentos.add(new Position(r, c)); 
                    }
                    break; 
                }
                r += d[0];
                c += d[1];
            }
        }
    }

    private void addMovimentosUnicos(Position position, int[][] moves, List<Position> movimentos) {
        Piece origem = tabuleiro.getPeca(position.getLinha(), position.getColuna());
        for (int[] m : moves) {
            int r = position.getLinha() + m[0];
            int c = position.getColuna() + m[1];
            if (!posicaoDentroDoTabuleiro(r, c)) continue;
            Piece alvo = tabuleiro.getPeca(r, c);
            if (alvo == null || alvo.getColor() != origem.getColor()) {
                movimentos.add(new Position(r, c));
            }
        }
    }

    private void addPeaoMovimentos(Position position, PieceColor color, List<Position> movimentos) {
        int dir = (color == PieceColor.BRANCO) ? -1 : 1;
        int r = position.getLinha();
        int c = position.getColuna();

        int r1 = r + dir;
        if (posicaoDentroDoTabuleiro(r1, c) && tabuleiro.getPeca(r1, c) == null) {
            movimentos.add(new Position(r1, c));

            int startRow = (color == PieceColor.BRANCO) ? 6 : 1;
            int r2 = r + 2 * dir;
            if (r == startRow && posicaoDentroDoTabuleiro(r2, c)
                    && tabuleiro.getPeca(r2, c) == null) {
                movimentos.add(new Position(r2, c));
            }
        }

        int[] cols = {c - 1, c + 1};
        for (int nc : cols) {
            if (!posicaoDentroDoTabuleiro(r1, nc)) continue;
            Piece alvo = tabuleiro.getPeca(r1, nc);
            if (alvo != null && alvo.getColor() != color) {
                movimentos.add(new Position(r1, nc));
            }
        }
    }

    // ==================== ATAQUES (para cheque) ====================

    private boolean casaEstaAtacadaPor(Position casa, PieceColor atacante) {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                Piece p = tabuleiro.getPeca(r, c);
                if (p == null || p.getColor() != atacante) continue;
                if (pecaAtacaCasa(new Position(r, c), casa, p)) return true;
            }
        }
        return false;
    }

    private boolean pecaAtacaCasa(Position origem, Position alvo, Piece peca) {
        String nome = peca.getClass().getSimpleName();

        switch (nome) {
            case "Peao": {
                int dir = (peca.getColor() == PieceColor.BRANCO) ? -1 : 1;
                int r = origem.getLinha() + dir;
                int c1 = origem.getColuna() - 1;
                int c2 = origem.getColuna() + 1;
                if (posicaoDentroDoTabuleiro(r, c1) && alvo.getLinha() == r && alvo.getColuna() == c1) return true;
                if (posicaoDentroDoTabuleiro(r, c2) && alvo.getLinha() == r && alvo.getColuna() == c2) return true;
                return false;
            }
            case "Cavalo": {
                int[][] m = {{2,1},{2,-1},{-2,1},{-2,-1},{1,2},{-1,2},{1,-2},{-1,-2}};
                for (int[] d : m) {
                    int r = origem.getLinha() + d[0], c = origem.getColuna() + d[1];
                    if (posicaoDentroDoTabuleiro(r, c) && r == alvo.getLinha() && c == alvo.getColuna()) return true;
                }
                return false;
            }
            case "Rei": {
                int[][] m = {{1,0},{-1,0},{0,1},{0,-1},{1,1},{-1,-1},{1,-1},{-1,1}};
                for (int[] d : m) {
                    int r = origem.getLinha() + d[0], c = origem.getColuna() + d[1];
                    if (posicaoDentroDoTabuleiro(r, c) && r == alvo.getLinha() && c == alvo.getColuna()) return true;
                }
                return false;
            }
            case "Torre":
                return linhaAtacaCasa(origem, alvo, new int[][]{{1,0},{-1,0},{0,1},{0,-1}});
            case "Bispo":
                return linhaAtacaCasa(origem, alvo, new int[][]{{1,1},{-1,-1},{1,-1},{-1,1}});
            case "Rainha":
                return linhaAtacaCasa(origem, alvo, new int[][]{{1,0},{-1,0},{0,1},{0,-1},{1,1},{-1,-1},{1,-1},{-1,1}});
        }
        return false;
    }

    private boolean linhaAtacaCasa(Position origem, Position alvo, int[][] dirs) {
        for (int[] d : dirs) {
            int r = origem.getLinha() + d[0];
            int c = origem.getColuna() + d[1];
            while (posicaoDentroDoTabuleiro(r, c)) {
                Piece p = tabuleiro.getPeca(r, c);
                if (r == alvo.getLinha() && c == alvo.getColuna()) return true;
                if (p != null) break;
                r += d[0];
                c += d[1];
            }
        }
        return false;
    }

    // ==================== UTIL / SIMULAÇÃO ====================

    private boolean posicaoDentroDoTabuleiro(Position pos) {
        return posicaoDentroDoTabuleiro(pos.getLinha(), pos.getColuna());
    }

    private boolean posicaoDentroDoTabuleiro(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }

    private boolean simularMovimentoMantendoEstado(Position origemPos, Position destinoPos, Verificador verificador) {
        Piece origem = tabuleiro.getPeca(origemPos.getLinha(), origemPos.getColuna());
        Piece destino = tabuleiro.getPeca(destinoPos.getLinha(), destinoPos.getColuna());

        Position origemPosObj = (origem != null) ? origem.getPosition() : null;
        Position destinoPosObj = (destino != null) ? destino.getPosition() : null;

        tabuleiro.setPeca(destinoPos.getLinha(), destinoPos.getColuna(), origem);
        tabuleiro.setPeca(origemPos.getLinha(), origemPos.getColuna(), null);
        if (origem != null) origem.setPosition(new Position(destinoPos.getLinha(), destinoPos.getColuna()));

        boolean resultado;
        try {
            resultado = verificador.testar();
        } finally {
            tabuleiro.setPeca(origemPos.getLinha(), origemPos.getColuna(), origem);
            tabuleiro.setPeca(destinoPos.getLinha(), destinoPos.getColuna(), destino);
            if (origem != null) origem.setPosition(origemPosObj);
            if (destino != null) destino.setPosition(destinoPosObj);
        }
        return resultado;
    }
    

    @FunctionalInterface
    private interface Verificador {
        boolean testar();
    }
}
