/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Comparator;

/**
 * Classe que implementa a ordenação de transações por data de forma decrescente.
 * @author vinim
 */
public class OrdenacaoPorDataDecrescente implements Comparator<Transacao>{

    /**
     * Compara duas transações para ordenação por data de forma decrescente.
     * 
     * @param o1 a primeira transação
     * @param o2 a segunda transação
     * @return um valor negativo, zero ou positivo se a primeira data for posterior, igual ou anterior à segunda, respectivamente
     */
    @Override
    public int compare(Transacao o1, Transacao o2) {
        int ordem = 0;//o1.compareTo(o2.getNome());
        if (ordem == 0) {
            if (o1.getDate().isAfter(o2.getDate())) {
            ordem = -1;
        } else if (o1.getDate().isBefore(o2.getDate())) {
            ordem = +1;
        } else {
            ordem = 0;
        }
    }
return ordem;
}
    
    
    
}
