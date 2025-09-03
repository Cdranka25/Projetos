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

public class GameXadrezGUI extends JFrame {
    private final CasaDoTabuleiro[][] casas = new CasaDoTabuleiro[8][8];
    private final GameXadrez game = new GameXadrez();

    private Position guiOriginForAnimation = null;
    private String guiSymbolForAnimation = null;
    private Color guiColorForAnimation = null;
    private boolean animating = false;

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
        try {
            java.util.List<Image> icons = new java.util.ArrayList<>();
            icons.add(new ImageIcon("src/resources/media/img/xadrez.png").getImage());
            setIconImages(icons);
        } catch (Exception e) {
            System.out.println("Ícone não encontrado.");
        }
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

        if (!animating && !game.pecaEstaSelecionada()) {
            String txt = casas[linha][Coluna].getText();
            if (txt != null && !txt.isEmpty()) {
                guiOriginForAnimation = new Position(linha, Coluna);
                guiSymbolForAnimation = txt;
                guiColorForAnimation = casas[linha][Coluna].getForeground();
            } else {
                guiOriginForAnimation = null;
                guiSymbolForAnimation = null;
                guiColorForAnimation = null;
            }
        }

        boolean moverResultado = game.tratarSelecaoDeQuadrado(linha, Coluna);
        removerDestaque();

        if (moverResultado) {
            if (!animating && guiOriginForAnimation != null && guiSymbolForAnimation != null) {
                Position origem = guiOriginForAnimation;
                Position destino = new Position(linha, Coluna);

                String symbolToAnimate = guiSymbolForAnimation;
                Color colorToAnimate = guiColorForAnimation;

                guiOriginForAnimation = null;
                guiSymbolForAnimation = null;
                guiColorForAnimation = null;

                casas[origem.getLinha()][origem.getColuna()].clearPieceSymbol();

                animatePiece(origem, destino, symbolToAnimate, colorToAnimate, () -> {
                    atualizarTabuleiro();
                    verificarEstadoDeJogo();
                    verificarGameOver();
                });

                return;
            }
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
        boolean emChequeMate = game.esta_em_ChequekMate(JogadorAtual);

        if (emCheque && !emChequeMate) {
            JOptionPane.showMessageDialog(this, JogadorAtual + " está em cheque!");
        }
    }

    private void destacarMovimentosPossiveis(Position position) {

        List<Position> pseudo = game.getPseudoMovimentosParaPeca(position);
        List<Position> legais = game.getMovimentosPossiveisParaPeca(position);

        TabuleiroDeXadrez tabuleiro = game.getTabuleiro();
        Piece pecaSelecionada = tabuleiro.getPeca(position.getLinha(), position.getColuna());

        for (Position move : legais) {
            Piece alvo = tabuleiro.getPeca(move.getLinha(), move.getColuna());

            if (alvo != null && alvo.getColor() != pecaSelecionada.getColor()) {
                casas[move.getLinha()][move.getColuna()].setBackground(Color.YELLOW);
            } else {
                casas[move.getLinha()][move.getColuna()].setBackground(Color.GREEN);
            }
        }

        for (Position move : pseudo) {
            boolean isLegal = false;
            for (Position l : legais) {
                if (l.getLinha() == move.getLinha() && l.getColuna() == move.getColuna()) {
                    isLegal = true;
                    break;
                }
            }
            if (!isLegal) {
                casas[move.getLinha()][move.getColuna()].setBackground(Color.RED);
            }
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
        resetItem.addActionListener(_ -> resetGame());
        gameMenu.add(resetItem);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    private void resetGame() {
        game.resetGame();
        atualizarTabuleiro();
    }

    private void verificarGameOver() {
        if (game.esta_em_ChequekMate(game.getJogadorAtualColor())) {
            int resposta = JOptionPane.showConfirmDialog(this, "Cheque Mate! Gostaria de jogar Novamente?", "Game Over",
                    JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                System.exit(0);
            }
        }
    }

    private void animatePiece(Position origem, Position destino, String symbol, Color color, Runnable onComplete) {
        if (origem == null || destino == null || symbol == null || symbol.isEmpty()) {
            if (onComplete != null)
                onComplete.run();
            return;
        }

        JLabel moving = new JLabel(symbol);
        moving.setFont(new Font("Serif", Font.BOLD, 36));
        if (color != null)
            moving.setForeground(color);
        moving.setSize(moving.getPreferredSize());

        JLayeredPane layered = getLayeredPane();

        CasaDoTabuleiro origemCasa = casas[origem.getLinha()][origem.getColuna()];
        CasaDoTabuleiro destinoCasa = casas[destino.getLinha()][destino.getColuna()];

        Rectangle oBounds = origemCasa.getBounds();
        Rectangle dBounds = destinoCasa.getBounds();

        Point oOnLayer = SwingUtilities.convertPoint(origemCasa.getParent(), oBounds.getLocation(), layered);
        Point dOnLayer = SwingUtilities.convertPoint(destinoCasa.getParent(), dBounds.getLocation(), layered);

        Point originPoint = new Point(oOnLayer.x + oBounds.width / 2 - moving.getWidth() / 2,
                oOnLayer.y + oBounds.height / 2 - moving.getHeight() / 2);
        Point destPoint = new Point(dOnLayer.x + dBounds.width / 2 - moving.getWidth() / 2,
                dOnLayer.y + dBounds.height / 2 - moving.getHeight() / 2);

        moving.setLocation(originPoint);
        layered.add(moving, JLayeredPane.POPUP_LAYER);

        animating = true;

        int durationMs = 220;
        int frames = 18;
        int delay = Math.max(1, durationMs / frames);
        final double dx = (destPoint.x - originPoint.x) / (double) frames;
        final double dy = (destPoint.y - originPoint.y) / (double) frames;
        final int[] frame = { 0 };

        Timer timer = new Timer(delay, e -> {
            frame[0]++;
            int x = (int) Math.round(originPoint.x + dx * frame[0]);
            int y = (int) Math.round(originPoint.y + dy * frame[0]);
            moving.setLocation(x, y);
            if (frame[0] >= frames) {
                ((Timer) e.getSource()).stop();
                layered.remove(moving);
                layered.repaint();
                animating = false;
                if (onComplete != null)
                    onComplete.run();
            }
        });
        timer.start();
    }
}
