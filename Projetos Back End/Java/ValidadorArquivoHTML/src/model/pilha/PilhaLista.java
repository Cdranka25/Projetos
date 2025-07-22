/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.pilha;

import model.listaEncadeada.*;

/**
 *
 * @author vinim
 */
public class PilhaLista<T> implements Pilha<T>{

    private ListaEncadeada<T> lista = new ListaEncadeada<>();

    @Override
    public void push(T info) {
        lista.inserir(info);
    }

    @Override
    public T pop() {
        T valor = peek();
        lista.retirar(valor);

        return valor;
    }

    @Override
    public T peek() {
        if(lista.estaVazia()){
            throw new PilhaVaziaException();
        }

        return lista.getUltimo().getInfo();
    }

    @Override
    public boolean estaVazia() {
        return lista.estaVazia();
    }

    @Override
    public void liberar(){
        try{
            while(true){
                pop();
            }
        } catch (PilhaVaziaException e){

        }
    }
    
    @Override
    public String toString(){
        String stringContent = "";
        NoLista<T> noToString = lista.getUltimo();
        while(noToString != null){
            if (noToString != lista.getUltimo()) {
                stringContent += "\n";
            }
            stringContent += noToString.getInfo();
            noToString = noToString.getProximo();
        }

        return stringContent;
    }
}
