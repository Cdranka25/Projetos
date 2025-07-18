/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Formulas.Hipotenusa;

/**
 *
 * @author user
 */
public class Hipotenusa {
    
    private double cateto1;
    private double cateto2;
    private double hipotenusa;

    public double getCateto1() {
        return cateto1;
    }

    public void setCateto1(double cateto1) {
        this.cateto1 = cateto1;
    }

    public double getCateto2() {
        return cateto2;
    }

    public void setCateto2(double cateto2) {
        this.cateto2 = cateto2;
    }
    
    public double calculoHip(){
        
        double cat1 = Math.pow(getCateto1(), 2);
        double cat2 = Math.pow(getCateto2(), 2);
       
        hipotenusa = Math.sqrt(getCateto1() + getCateto2());
        return hipotenusa;
    }
}
