/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import model.Operacoes.Subtracao;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author user
 */
public class SubtracaoTest {
    
private Subtracao subtracao;

    @Before
    public void setUp() {
        subtracao = new Subtracao();
    }

    @Test
    public void testCalculo() {
        subtracao.setNum1Sub(10);
        subtracao.setNum2Sub(2);
        double result = subtracao.calculo();
        assertEquals(8.0, result, 0.001);
    }

    @Test
    public void testGetResultadoSub() {
        subtracao.setNum1Sub(15);
        subtracao.setNum2Sub(3);
        double result = subtracao.getResultadoSub();
        assertEquals(12.0, result, 0.001);
    }

    @Test
    public void testSetAndGetNum1Sub() {
        subtracao.setNum1Sub(7);
        assertEquals(7, subtracao.getNum1Sub(), 0.001);
    }

    @Test
    public void testSetAndGetNum2Sub() {
        subtracao.setNum2Sub(8);
        assertEquals(8, subtracao.getNum2Sub(), 0.001);
    }
}