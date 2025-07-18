/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Imposto.IPTU;

/**
 *
 * @author user
 */
public class Bairro {

    private String nome = "";
    private double coeficienteIptu = 1.0;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }

    public double getCoeficienteIptu() {
        return coeficienteIptu;
    }

    public void setCoeficienteIptu(double coeficienteIptu) {
        this.coeficienteIptu = coeficienteIptu;
    }

}
