package model;

import model.Pecas.Bispo;
import model.Pecas.Cavalo;
import model.Pecas.Peao;
import model.Pecas.Peca;
import model.Pecas.Rainha;
import model.Pecas.Rei;
import model.Pecas.Torre;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author cdran
 */
public class Tabuleiro {

    private Peca[][] tabuleiro;

    public Tabuleiro() {
        tabuleiro = new Peca[8][8];
    }

    public void inicializar() {
        // Adicionar peões brancos
        for (int coluna = 0; coluna < 8; coluna++) {
            tabuleiro[1][coluna] = new Peao(true, 1, coluna);
        }
        // Adicionar peões pretos
        for (int coluna = 0; coluna < 8; coluna++) {
            tabuleiro[6][coluna] = new Peao(false, 6, coluna);
        }

        // Adicionar torres brancas
        for (int coluna = 0; coluna < 8; coluna++) {
            tabuleiro[0][coluna] = new Torre(true, 0, 0);
            tabuleiro[0][coluna] = new Torre(true, 0, 7);
        }
        // Adicionar torres pretas
        for (int coluna = 0; coluna < 8; coluna++) {
            tabuleiro[7][coluna] = new Torre(false, 7, 0);
            tabuleiro[7][coluna] = new Torre(false, 7, 7);
        }

        // Adicionar cavalos brancos
        for (int coluna = 0; coluna < 8; coluna++) {
            tabuleiro[0][coluna] = new Cavalo(true, 0, 1);
            tabuleiro[0][coluna] = new Cavalo(true, 0, 6);
        }
        // Adicionar cavalos pretos
        for (int coluna = 0; coluna < 8; coluna++) {
            tabuleiro[7][coluna] = new Cavalo(false, 7, 1);
            tabuleiro[7][coluna] = new Cavalo(false, 7, 6);
        }

        // Adicionar bispos brancos
        for (int coluna = 0; coluna < 8; coluna++) {
            tabuleiro[0][coluna] = new Bispo(true, 0, 2);
            tabuleiro[0][coluna] = new Bispo(true, 0, 5);
        }
        // Adicionar bispos pretos
        for (int coluna = 0; coluna < 8; coluna++) {
            tabuleiro[7][coluna] = new Bispo(false, 7, 2);
            tabuleiro[7][coluna] = new Bispo(false, 7, 5);
        }

        // Adicionar Reis brancos
        for (int coluna = 0; coluna < 8; coluna++) {
            tabuleiro[0][coluna] = new Rei(true, 0, 3);
        }
        // Adicionar Reis pretos
        for (int coluna = 0; coluna < 8; coluna++) {
            tabuleiro[7][coluna] = new Rei(false, 7, 3);
        }

        // Adicionar Rainhas brancas
        for (int coluna = 0; coluna < 8; coluna++) {
            tabuleiro[0][coluna] = new Rainha(true, 0, 4);
        }
        // Adicionar Rainhas pretas
        for (int coluna = 0; coluna < 8; coluna++) {
            tabuleiro[7][coluna] = new Rainha(false, 7, 4);
        }

    }
}
