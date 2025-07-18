/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import org.junit.Before;
import org.junit.Test;

import model.Despesas;
import model.TipoDespesa;
import model.TipoTransacao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author user
 */
public class DespesasTest {
    
    private Despesas despesa;

    @Before
    public void setUp() {
        TipoDespesa tipoDespesa = TipoDespesa.Alimentacao;
        String descricao = "Compra de supermercado";
        double valor = 200.0;
        TipoTransacao tipoTransacao = TipoTransacao.Despesa;
        LocalDate data = LocalDate.now();

        despesa = new Despesas(tipoDespesa, descricao, valor, tipoTransacao, data);
    }

    @Test
    public void testConstrutorComParametros() {
        assertEquals(TipoDespesa.Alimentacao, despesa.getTipoDespesa());
        assertEquals("Compra de supermercado", despesa.getDescricao());
        assertEquals(200.0, despesa.getValor(), 0.01);
        assertEquals(TipoTransacao.Despesa, despesa.getTipoTransacao());
        assertEquals(LocalDate.now(), despesa.getDate());
    }

    @Test
    public void testGetDescricao() {
        assertEquals("Compra de supermercado", despesa.getDescricao());
    }

    @Test
    public void testSetDescricao() {
        despesa.setDescricao("Nova descrição");
        assertEquals("Nova descrição", despesa.getDescricao());
    }

    @Test
    public void testGetTipoDespesa() {
        assertEquals(TipoDespesa.Alimentacao, despesa.getTipoDespesa());
    }

    @Test
    public void testSetTipoDespesa() {
        despesa.setTipoDespesa(TipoDespesa.Saude);
        assertEquals(TipoDespesa.Saude, despesa.getTipoDespesa());
    }

    @Test
    public void testGetTipoTransacao() {
        assertEquals(TipoTransacao.Despesa, despesa.getTipoTransacao());
    }

    @Test
    public void testCriaDespesa() {
        String[] atributos = {"Saude", "Consulta médica", "150.0", "Despesa", "15-06-2024"};
        Despesas novaDespesa = despesa.criaDespesa(atributos);

        assertEquals(TipoDespesa.Saude, novaDespesa.getTipoDespesa());
        assertEquals("Consulta médica", novaDespesa.getDescricao());
        assertEquals(150.0, novaDespesa.getValor(), 0.01);
        assertEquals(TipoTransacao.Despesa, novaDespesa.getTipoTransacao());
        assertEquals(LocalDate.of(2024, 6, 15), novaDespesa.getDate());
    }
}