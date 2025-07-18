/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author user
 */
public class Utilitario {

    public void InserirIcone(JFrame frm) {
        try {
            frm.setIconImage(Toolkit.getDefaultToolkit().getImage("ico/ListaDeTarefas.png"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
