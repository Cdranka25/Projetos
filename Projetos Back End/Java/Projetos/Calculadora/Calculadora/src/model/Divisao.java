/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


/**
 * A classe Divisao representa uma operação de divisão entre dois números.
 * Implementa a interface Num.
 * 
 * A classe contém métodos para configurar os valores dos números a serem divididos,
 * bem como para calcular e obter o resultado da divisão.
 * 
 * Exemplos de uso:
 * Divisao div = new Divisao();
 * div.setNum1Div(10);
 * div.setNum2Div(2);
 * double resultado = div.getResultadoDiv();  // resultado será 5.0
 * 
 * Nota: Esta classe não lida com a divisão por zero. Deve-se garantir que o divisor não seja zero antes de realizar a operação.
 * 
 * @autor Cdranka
 */
public class Divisao implements Num {

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
        if (num2Div == 0) {
            throw new ArithmeticException("Impossível Dividir");
        }
        return resultadoDiv = num1Div / num2Div;
    }
}
