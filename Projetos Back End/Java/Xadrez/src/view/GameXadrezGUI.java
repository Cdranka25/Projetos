/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;

import model.Game.CasaDoTabuleiro;
import model.Game.GameXadrez;
import model.Game.TabuleiroDeXadrez;
import model.config.*;
import model.controller.Piece;
import model.controller.PieceColor;
import model.controller.Position;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 *
 * @author cdran
 */
public class GameXadrezGUI extends JFrame {
    private final CasaDoTabuleiro[][] casas = new CasaDoTabuleiro[8][8];
    private final GameXadrez game = new GameXadrez();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameXadrezGUI::new);
    }

    private final Map<Class<? extends Piece>, String> pieceUnicodeMap = new HashMap<>() {
        {
            put(Peao.class, "\u265F");
            put(Torre.class, "\u265C");
            put(Cavalo.class, "\u265E");
            put(Bispo.class, "\u265D");
            put(Rainha.class, "\u265B");
            put(Rei.class, "\u265A");
        }
    };

    public GameXadrezGUI() {
        setTitle("Game de Xadrez");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));
        inicializarTabuleiro();
        addGameResetOption();
        pack();
        setMinimumSize(new Dimension(640, 640));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void inicializarTabuleiro() {
        for (int linha = 0; linha < casas.length; linha++) {
            for (int Coluna = 0; Coluna < casas[linha].length; Coluna++) {
                final int finalLinha = linha;
                final int finalColuna = Coluna;
                CasaDoTabuleiro casa = new CasaDoTabuleiro(linha, Coluna);
                casa.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        tratarCliqueNaCasa(finalLinha, finalColuna);
                    }
                });
                add(casa);
                casas[linha][Coluna] = casa;
            }
        }
        atualizarTabuleiro();
    }

    private void atualizarTabuleiro() {
        TabuleiroDeXadrez tabuleiro = game.getTabuleiro();
        for (int linha = 0; linha < 8; linha++) {
            for (int Coluna = 0; Coluna < 8; Coluna++) {
                Piece piece = tabuleiro.getPeca(linha, Coluna);
                if (piece != null) {
                    String symbol = pieceUnicodeMap.get(piece.getClass());
                    Color color = (piece.getColor() == PieceColor.BRANCO) ? Color.WHITE : Color.BLACK;
                    casas[linha][Coluna].setPieceSymbol(symbol, color);

                } else {
                    casas[linha][Coluna].clearPieceSymbol();
                }
            }
        }
    }

    private void tratarCliqueNaCasa(int linha, int Coluna) {
        boolean moverResultado = game.tratarSelecaoDeQuadrado(linha, Coluna);
        removerDestaque();

        if (moverResultado) {
            atualizarTabuleiro();
            verificarEstadoDeJogo();
            verificarGameOver();
        } else if (game.pecaEstaSelecionada()) {
            destacarMovimentosPossiveis(new Position(linha, Coluna));
        }
        atualizarTabuleiro();
    }

    private void verificarEstadoDeJogo() {
        PieceColor JogadorAtual = game.getJogadorAtualColor();
        boolean emCheque = game.esta_Em_Cheque(JogadorAtual);

        if (emCheque) {
            JOptionPane.showMessageDialog(this, JogadorAtual + " Está em cheque!");
        }
    }

    private void destacarMovimentosPossiveis(Position position) {
        List<Position> movimentosPossiveis = game.getMovimentosPossiveisParaPeca(position);
        for (Position move : movimentosPossiveis) {
            casas[move.getLinha()][move.getColuna()].setBackground(Color.GREEN);
        }
    }

    private void removerDestaque() {
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                casas[linha][coluna]
                        .setBackground((linha + coluna) % 2 == 0 ? Color.LIGHT_GRAY : new Color(205, 133, 63));
            }
        }
    }

    private void addGameResetOption() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem resetItem = new JMenuItem("Reset");
        resetItem.addActionListener(e -> resetGame());
        gameMenu.add(resetItem);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    private void resetGame() {
        game.resetGame();
        atualizarTabuleiro();
    }

    private void verificarGameOver(){
        if(game.esta_em_ChquekMate(game.getJogadorAtualColor())) {
            int resposta = JOptionPane.showConfirmDialog(this, "Chque Mate! Gostaria de jogar Novamente?", "Game Over", JOptionPane.YES_NO_OPTION);
            if(resposta == JOptionPane.YES_OPTION){
                resetGame();
            } else {
                System.exit(0);
            }
        }
    }
}