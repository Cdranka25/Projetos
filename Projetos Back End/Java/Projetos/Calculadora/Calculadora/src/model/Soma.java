/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * A classe Soma representa uma operação de soma entre dois números.
 * Implementa a interface Num.
 * 
 * A classe contém métodos para configurar os valores dos números a serem somados,
 * bem como para calcular e obter o resultado da soma.
 * 
 * Exemplos de uso:
 * Soma soma = new Soma();
 * soma.setNum1Soma(10);
 * soma.setNum2Soma(2);
 * double resultado = soma.getResultadoSoma();  // resultado será 12.0
 * 
 * @autor Cdranka
 */
public class Soma implements Num{
    
    private double num1Soma;
    private double num2Soma;
    private double resultadoSoma;

    public Soma() {
    }

    public double getNum1Soma() {
        return num1Soma;
    }

    public void setNum1Soma(double num1Soma) {
        this.num1Soma = num1Soma;
    }

    public double getNum2Soma() {
        return num2Soma;
    }

    public void setNum2Soma(double num2Soma) {
        this.num2Soma = num2Soma;
    }

    public double getResultadoSoma() {
       calculo();
        return resultadoSoma;
    }

    public void setResultadoSoma(double resultadoSoma) {
        this.resultadoSoma = resultadoSoma;
    }

    public double calculo() {
        return resultadoSoma = (num1Soma + num2Soma);
    }
    
    
    
}
