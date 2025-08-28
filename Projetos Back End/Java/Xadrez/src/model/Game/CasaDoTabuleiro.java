package model.Game;

import javax.swing.*;
import java.awt.*;

public class CasaDoTabuleiro extends JButton{
    private int linha;
    private int coluna;

    public CasaDoTabuleiro(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
        initButton();
    }

    private void initButton() {
        setPreferredSize(new Dimension(64,64));
        
        if((linha + coluna) %2 ==0) {
            setBackground(Color.LIGHT_GRAY);
        } else {
            setBackground(new Color(205, 133, 63));
        }

        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);

        setFont(new Font("Serif", Font.BOLD, 36));
    }

    public void setPieceSymbol(String symbol, Color color) {
        this.setText(symbol);
        this.setForeground(color);
    }

    public void clearPieceSymbol() {
        this.setText("");
    }
}
