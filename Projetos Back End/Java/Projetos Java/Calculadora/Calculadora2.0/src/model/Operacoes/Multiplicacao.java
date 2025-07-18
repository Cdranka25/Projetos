/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Operacoes;

/**
 *
 * @author user
 */
public class Multiplicacao implements Num{
    
    private double num1Mult;
    private double num2Mult;
    private double resultadoMult;

    public Multiplicacao() {
    }

    public double getNum1Mult() {
        return num1Mult;
    }

    public void setNum1Mult(double num1Mult) {
        this.num1Mult = num1Mult;
    }

    public double getNum2Mult() {
        return num2Mult;
    }

    public void setNum2Mult(double num2Mult) {
        this.num2Mult = num2Mult;
    }

    public double getResultadoMult() {
        calculo();
        return resultadoMult;
    }

    public void setResultadoMult(double resultadoMult) {
        this.resultadoMult = resultadoMult;
    }

    public double calculo() {
        return resultadoMult = num1Mult * num2Mult;
    }
}
