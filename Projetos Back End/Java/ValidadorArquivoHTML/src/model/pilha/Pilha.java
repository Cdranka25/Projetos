/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.pilha;

/**
 *
 * @author vinim
 */
 interface Pilha<T> {

     void push(T info);
     T pop();
     T peek();
     boolean estaVazia();
     void liberar();
}
