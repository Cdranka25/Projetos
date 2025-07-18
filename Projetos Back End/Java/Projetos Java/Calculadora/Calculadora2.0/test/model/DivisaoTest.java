/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import model.Operacoes.Divisao;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author user
 */
public class DivisaoTest {

    private Divisao divisao;

    @Before
    public void setUp() {
        divisao = new Divisao();
    }

    @Test
    public void testCalculo() {
        divisao.setNum1Div(10);
        divisao.setNum2Div(2);
        double result = divisao.calculo();
        assertEquals(5.0, result, 0.001);
    }

    @Test
    public void testGetResultadoDiv() {
        divisao.setNum1Div(15);
        divisao.setNum2Div(3);
        double result = divisao.getResultadoDiv();
        assertEquals(5.0, result, 0.001);
    }

    @Test(expected = ArithmeticException.class)
    public void testDivisaoPorZero() {
        divisao.setNum1Div(10);
        divisao.setNum2Div(0);
        divisao.calculo();
    }
}
