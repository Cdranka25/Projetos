/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.TipoTransacao;
import model.Transacao;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author user
 */


public class TransacaoTest {

    private Transacao transacao;

    @Before
    public void setUp() {
        double valor = 100.0;
        TipoTransacao tipoTransacao = TipoTransacao.Receita;
        LocalDate data = LocalDate.now();

        transacao = new Transacao(valor, tipoTransacao, data);
    }

    @Test
    public void testConstrutorComParametros() {
        assertEquals(100.0, transacao.getValor(), 0.01);
        assertEquals(TipoTransacao.Receita, transacao.getTipoTransacao());
        assertEquals(LocalDate.now(), transacao.getDate());
    }

    @Test
    public void testGetValor() {
        assertEquals(100.0, transacao.getValor(), 0.01);
    }

    @Test
    public void testGetTipoTransacao() {
        assertEquals(TipoTransacao.Receita, transacao.getTipoTransacao());
    }

    @Test
    public void testGetDate() {
        assertEquals(LocalDate.now(), transacao.getDate());
    }

    @Test
    public void testSetValor() {
        transacao.setValor(200.0);
        assertEquals(200.0, transacao.getValor(), 0.01);
    }

    @Test
    public void testSetTipoTransacao() {
        transacao.setTipoTransacao(TipoTransacao.Despesa);
        assertEquals(TipoTransacao.Despesa, transacao.getTipoTransacao());
    }

    @Test
    public void testSetDate() {
        LocalDate novaData = LocalDate.of(2024, 6, 1);
        transacao.setDate(novaData);
        assertEquals(novaData, transacao.getDate());
    }

    @Test
    public void testToString() {
        String expected = "Transacao{valor=100.0, tipoTransacao=Receita, date=" + LocalDate.now() + "}";
        assertEquals(expected, transacao.toString());
    }
}