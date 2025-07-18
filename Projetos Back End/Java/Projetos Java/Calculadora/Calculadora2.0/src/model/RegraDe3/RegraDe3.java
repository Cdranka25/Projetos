/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.RegraDe3;

/**
 *
 * @author user
 */
public class RegraDe3 {
    
    private double valor1;
    private double valor2;
    private double valor3;
    private double valorDesconhecido;
    
    public double getValor1() {
        return valor1;
    }

    public void setValor1(double valor1) {
        this.valor1 = valor1;
    }

    public double getValor2() {
        return valor2;
    }

    public void setValor2(double valor2) {
        this.valor2 = valor2;
    }

    public double getValor3() {
        return valor3;
    }

    public void setValor3(double valor3) {
        this.valor3 = valor3;
    }
    
    public double getValorDesconhecido() {
       return valorDesconhecido = (getValor2() * getValor3() / getValor1());
    }
}
