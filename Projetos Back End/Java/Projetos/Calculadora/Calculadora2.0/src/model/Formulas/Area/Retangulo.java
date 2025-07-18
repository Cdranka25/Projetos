/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Formulas.Area;

/**
 *
 * @author user
 */
public class Retangulo extends Figura {
    
    private int largura;
    private int altura;
    
    public Retangulo(int largura, int altura) {
        this.altura = altura;
        this.largura = largura;
    }
    
    public double calcularArea() {
        return largura * altura;
    }
}
