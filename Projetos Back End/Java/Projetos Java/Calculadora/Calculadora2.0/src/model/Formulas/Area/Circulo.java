/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Formulas.Area;

/**
 *
 * @author user
 */
public class Circulo extends Figura{
    
    private int raio;
    
    public Circulo (int raio) {
        this.raio = raio;
    }
    
    public double calcularArea(){
       return Math.PI * Math.pow(raio, 2);
    }
}
