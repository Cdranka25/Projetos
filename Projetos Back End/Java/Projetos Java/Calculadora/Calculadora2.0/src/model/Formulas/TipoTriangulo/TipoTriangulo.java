/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Formulas.TipoTriangulo;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import model.Formulas.TipoTriangulo.Triangulo;

/**
 *
 * @author user
 */
public class TipoTriangulo {

    private double[] lados = new double[3];
    private Triangulo triangulo;

    private double A = lados[2];
    private double B = lados[1];
    private double C = lados[0];

    public double[] getLados() {
        return lados;
    }

    public void setLados(double[] lados) {
        this.lados = lados;
    }

    public Triangulo getTriangulo() {
        return triangulo;
    }

    public void setTriangulo(Triangulo triangulo) {
        this.triangulo = triangulo;
    }

    public double getA() {
        return A;
    }

    public void setA(double A) {
        this.A = A;
    }

    public double getB() {
        return B;
    }

    public void setB(double B) {
        this.B = B;
    }

    public double getC() {
        return C;
    }

    public void setC(double C) {
        this.C = C;
    }

    public Triangulo getResultado() {

        Arrays.sort(lados);

        if (getA() >= getB() + getC()) {
            return triangulo = triangulo.NaoFormaTriangulo;
        } else {
            if (Math.pow(getA(), 2) == Math.pow(getB(), 2) + Math.pow(getC(), 2)) {
                return triangulo = triangulo.TrianguloRetangulo;

            } else if (Math.pow(getA(), 2) > Math.pow(getB(), 2) + Math.pow(getC(), 2)) {
                return triangulo = triangulo.TrianguloObtusangulo;
            } else if (Math.pow(getA(), 2) < Math.pow(getB(), 2) + Math.pow(getC(), 2)) {
                return triangulo = triangulo.TrianguloAcutangulo;
            }
            if (A == B && B == C) {
                return triangulo = triangulo.TrianguloEquilatero;
            } else if (A == B || B == C || A == C) {
                return triangulo =  triangulo.TrianguloIsosceles;
            }
        } 
        return triangulo;
    }
}
 