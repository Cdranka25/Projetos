/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class Lista {

    private String tarefa;
    private Prioridades prioridade;
    private Categorias categoria;
    private boolean concluido;

    public Lista(String tarefa, Prioridades prioridade, Categorias categoria, boolean concluido) {
        this.tarefa = tarefa;
        this.concluido = concluido;
        this.prioridade = prioridade;
        this.categoria = categoria;
    }

    public String getTarefa() {
        return tarefa;
    }

    public void setTarefa(String tarefa) {
        this.tarefa = tarefa;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public Prioridades getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridades prioridades) {
        this.prioridade = prioridades;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categorias) {
        this.categoria = categorias;
    }

    public String toString() {
        // Converte o status "concluído" para "Sim" ou "Não"
        String tarefaConcluida = concluido ? "true" : "false";

        // Retorna os atributos separados por vírgulas
        return getTarefa() + ";" + getPrioridade() + ";" + getCategoria() + ";" + tarefaConcluida;
    }

}
