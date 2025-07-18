/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Operacoes;

/**
 *
 * @author user
 */
public class Subtracao implements Num{
    private double num1Sub;
    private double num2Sub;
    private double resultadoSub;

    public Subtracao() {
    }

    public double getNum1Sub() {
        return num1Sub;
    }

    public void setNum1Sub(double num1Sub) {
        this.num1Sub = num1Sub;
    }

    public double getNum2Sub() {
        return num2Sub;
    }

    public void setNum2Sub(double num2Sub) {
        this.num2Sub = num2Sub;
    }

    public double getResultadoSub() {
        calculo();
        return resultadoSub;
    }

    public void setResultadoSub(double resultadoSub) {
        this.resultadoSub = resultadoSub;
    }

    public double calculo() {
        return resultadoSub = (num1Sub - num2Sub);
    }
    
}
