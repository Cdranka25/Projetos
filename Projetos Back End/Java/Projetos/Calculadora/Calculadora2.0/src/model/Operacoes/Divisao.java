/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Operacoes;

/**
 *
 * @author user
 */
public class Divisao implements Num{
    
    private double num1Div;
    private double num2Div;
    private double resultadoDiv;

    public Divisao() {
    }

    public double getNum1Div() {
        return num1Div;
    }

    public void setNum1Div(double num1Div) {
        this.num1Div = num1Div;
    }

    public double getNum2Div() {
        return num2Div;
    }

    public void setNum2Div(double num2Div) {
        this.num2Div = num2Div;
    }

    public double getResultadoDiv() {
        calculo();
        return resultadoDiv;
    }

    public void setResultadoDiv(double resultadoDiv) {
        this.resultadoDiv = resultadoDiv;
    }
    
    public double calculo() {
        return resultadoDiv = num1Div / num2Div;
    }
}
