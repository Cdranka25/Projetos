/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import org.junit.Before;
import org.junit.Test;
import model.Receitas;
import model.TipoReceita;
import model.TipoTransacao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.junit.Assert.assertEquals;
/**
 *
 * @author user
 */

public class ReceitasTest {

    private Receitas receita;

    @Before
    public void setUp() {
        TipoReceita tipoReceita = TipoReceita.Outras_Receitas;
        String descricao = "Teste de Receita";
        double valor = 100.0;
        TipoTransacao tipoTransacao = TipoTransacao.Receita;
        LocalDate data = LocalDate.now();

        receita = new Receitas(tipoReceita, descricao, valor, tipoTransacao, data);
    }

    @Test
    public void testConstrutorComParametros() {
        assertEquals(TipoReceita.Outras_Receitas, receita.getTipoReceita());
        assertEquals("Teste de Receita", receita.getDescricao());
        assertEquals(100.0, receita.getValor(), 0.01);
        assertEquals(TipoTransacao.Receita, receita.getTipoTransacao());
        assertEquals(LocalDate.now(), receita.getDate());
    }

    @Test
    public void testGetDescricao() {
        assertEquals("Teste de Receita", receita.getDescricao());
    }

    @Test
    public void testSetDescricao() {
        receita.setDescricao("Nova Descrição");
        assertEquals("Nova Descrição", receita.getDescricao());
    }

    @Test
    public void testGetTipoReceita() {
        assertEquals(TipoReceita.Outras_Receitas, receita.getTipoReceita());
    }

    @Test
    public void testSetTipoReceita() {
        receita.setTipoReceita(TipoReceita.Salario);
        assertEquals(TipoReceita.Salario, receita.getTipoReceita());
    }

    @Test
    public void testGetTipoTransacao() {
        assertEquals(TipoTransacao.Receita, receita.getTipoTransacao());
    }

    @Test
    public void testCriaReceita() {
        String[] atributos = {"Salario", "Receita Mensal", "2000.0", "Receita", "15-06-2024"};
        Receitas novaReceita = receita.criaReceita(atributos);

        assertEquals(TipoReceita.Salario, novaReceita.getTipoReceita());
        assertEquals("Receita Mensal", novaReceita.getDescricao());
        assertEquals(2000.0, novaReceita.getValor(), 0.01);
        assertEquals(TipoTransacao.Receita, novaReceita.getTipoTransacao());
        assertEquals(LocalDate.of(2024, 6, 15), novaReceita.getDate());
    }
}
