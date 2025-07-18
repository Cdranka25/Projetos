/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import model.Operacoes.Soma;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author user
 */
public class SomaTest {
    

    private Soma soma;

    @Before
    public void setUp() {
        soma = new Soma();
    }

    @Test
    public void testCalculo() {
        soma.setNum1Soma(10);
        soma.setNum2Soma(2);
        double result = soma.calculo();
        assertEquals(12.0, result, 0.001);
    }

    @Test
    public void testGetResultadoSoma() {
        soma.setNum1Soma(15);
        soma.setNum2Soma(3);
        double result = soma.getResultadoSoma();
        assertEquals(18.0, result, 0.001);
    }

    @Test
    public void testSetAndGetNum1Soma() {
        soma.setNum1Soma(7);
        assertEquals(7, soma.getNum1Soma(), 0.001);
    }

    @Test
    public void testSetAndGetNum2Soma() {
        soma.setNum2Soma(8);
        assertEquals(8, soma.getNum2Soma(), 0.001);
    }
}
