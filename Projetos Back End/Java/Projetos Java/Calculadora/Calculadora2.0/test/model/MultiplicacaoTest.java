/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import model.Operacoes.Multiplicacao;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author user
 */
public class MultiplicacaoTest {
    
private Multiplicacao multiplicacao;

    @Before
    public void setUp() {
        multiplicacao = new Multiplicacao();
    }

    @Test
    public void testCalculo() {
        multiplicacao.setNum1Mult(10);
        multiplicacao.setNum2Mult(2);
        double result = multiplicacao.calculo();
        assertEquals(20.0, result, 0.001);
    }

    @Test
    public void testGetResultadoMult() {
        multiplicacao.setNum1Mult(15);
        multiplicacao.setNum2Mult(3);
        double result = multiplicacao.getResultadoMult();
        assertEquals(45.0, result, 0.001);
    }

    @Test
    public void testSetAndGetNum1Mult() {
        multiplicacao.setNum1Mult(7);
        assertEquals(7, multiplicacao.getNum1Mult(), 0.001);
    }

    @Test
    public void testSetAndGetNum2Mult() {
        multiplicacao.setNum2Mult(8);
        assertEquals(8, multiplicacao.getNum2Mult(), 0.001);
    }
}