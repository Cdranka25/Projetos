/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Formulas.Diagrama;

/**
 *
 * @author caiod
 */
public class Ponto {

    private int x;
    private int y;

    public Ponto() {
    }

    public Ponto(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public Quadrante identificarQuadrante(){
        
        if(getX() == 0 || getY() == 0) {
            return Quadrante.NENHUM;
        } else if (getX() > 0) {
            if (getY() > 0) {
                return Quadrante.PRIMEIRO;
            } else {
                return Quadrante.QUARTO;
            }
        } else {
            if(getY()>0) {
                return Quadrante.SEGUNDO;
            }else{
                return  Quadrante.TERCEIRO;
            }
        }
        
    }
    
    public boolean estaIncidindoSobreX(){
        return getY() == 0;
    }
    public boolean estaIncindindoSobreY () {
        return getX() == 0;
    }
    
    public double calcularDistancia(Ponto outroPonto) {
        
        int x1 = this.x;
        int y1 = this.y;
        
        int x2 = outroPonto.getX();
        int y2 = outroPonto.getY();
        
        double d = Math.pow(x2 - x1, 2) + Math.pow(y2- y1, 2);
        
        return d;
    }
    public static double calcularDistancia(Ponto p1, Ponto p2) {
        return p1.calcularDistancia(p2);
    }

}
