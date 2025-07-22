/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Imposto.IPTU;

/**
 *
 * @author user
 */
public class Imovel {

    private double area = 0;
    private Bairro bairro;
    private Finalidade finalidade;

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        if (area <= 0) {
            throw new IllegalArgumentException("Fatal Error - Área deve ter um valor Positivo");
        }
        this.area = area;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {

        this.bairro = bairro;
    }

    public Finalidade getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(Finalidade finalidade) {

        this.finalidade = finalidade;

    }

    public double calcularIptu() {
        double resultado = 0;

        if (finalidade == null) {
            throw new IllegalArgumentException("Fatal Error - A Finalidade não foi Preenchida");
        }
        if (getBairro().getNome() == null) {
            throw new IllegalArgumentException("Fatal Error - Bairro não foi definido");
        }
        if (getBairro().getCoeficienteIptu() < 0 ){
            throw new IllegalArgumentException("Fatal Error - Coeficiente deve ser um valor Positivo");
        }

        if (null != getFinalidade()) {
            switch (getFinalidade()) {
                case RESIDENCIAL ->
                    resultado = getArea() * bairro.getCoeficienteIptu();
                case COMERCIAL -> {
                    if (getArea() <= 100) {
                        resultado = 500 * bairro.getCoeficienteIptu();
                    } else if (getArea() > 100 && getArea() <= 400) {
                        resultado = 1000 * bairro.getCoeficienteIptu();
                    } else if (getArea() > 400) {
                        resultado = (500 * 2.55) * bairro.getCoeficienteIptu();
                    }
                }
                case INDUSTRIAL -> {
                    if (getArea() <= 2000) {
                        resultado = 1000 * bairro.getCoeficienteIptu();
                    } else if (getArea() > 2000) {
                        resultado = 0.55 * getArea() * bairro.getCoeficienteIptu();
                    }
                }
                default -> {
                }
            }
        }

        return resultado;
    }
}
