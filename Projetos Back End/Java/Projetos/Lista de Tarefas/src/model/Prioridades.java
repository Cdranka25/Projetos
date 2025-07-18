/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package model;

/**
 *
 * @author user
 */
public enum Prioridades {
    URGENTE("Urgente"),
    ALTA("Alta"),
    MEDIA("Média"),
    BAIXA("Baixa"),
    MINIMA("Mínima");

    private final String descricao;

    // Construtor do enum
    Prioridades(String descricao) {
        this.descricao = descricao;
    }

    // Sobrescreve o método toString() para retornar a descrição desejada
    @Override
    public String toString() {
        return descricao;
    }

    // Método para converter uma string em um enum Prioridades
    public static Prioridades fromString(String descricao) {
        for (Prioridades p : Prioridades.values()) {
            if (p.descricao.equalsIgnoreCase(descricao.trim())) {
                return p;
            }
        }
        throw new IllegalArgumentException("Prioridade inválida: " + descricao);
    }
}
