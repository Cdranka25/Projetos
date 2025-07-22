/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package model;

/**
 *
 * @author user
 */
public enum Categorias {

    ALIMENTACAO("Alimentação"),
    PROFISSIONAL("Profissional"),
    TRANSPORTE("Transporte"),
    ESTUDO("Estudo"),
    LAZER("Lazer"),
    SAUDE("Saúde"),
    EDUCACAO("Educação"),
    OUTROS("Outros");

    private final String descricao;

    Categorias(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public static Categorias fromString(String texto) {
        for (Categorias categoria : Categorias.values()) {
            if (categoria.descricao.equalsIgnoreCase(texto)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Categoria inválida: " + texto);
    }
}
