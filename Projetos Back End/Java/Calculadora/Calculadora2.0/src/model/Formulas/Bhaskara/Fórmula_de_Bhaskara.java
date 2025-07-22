/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Formulas.Bhaskara;


/**
 *
 * @author user
 */
public class Fórmula_de_Bhaskara {

    private double a;
    private double b;
    private double c;
    private double x1 = 0;
    private double x2 = 0;
    private double formulaDelta;

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public void calculo() {
        double raizQuadradaDelta;
        formulaDelta = (b * b) - (4 * a * c);
        if (a == 0 || formulaDelta < 0) {
            throw new IllegalArgumentException("Impossível calcular");
        } else {
            raizQuadradaDelta = Math.sqrt(formulaDelta);
            x1 = (-b + raizQuadradaDelta) / (2 * a);
            x2 = (-b - raizQuadradaDelta) / (2 * a);
        }
    }
    public double getResultadoX1(){
        return x1;
    }
        public double getResultadoX2(){
        return x2;
    }
}

