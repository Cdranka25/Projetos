/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *Classe responsável por exportar transações para um arquivo.
 * @author vinim
 */

public class ExportadorArquivo {

    private File file;
    private Conta conta = new Conta();

    
    /**
     * Grava a lista de transações em um arquivo.
     * 
     * @param transacoes a lista de transações a ser gravada
     */
    public void gravarArquivo(ArrayList<Transacao> transacoes) {
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            fw = new FileWriter(file);
            writer = new BufferedWriter(fw);
            
            String cabecalho = "TipoReceita;Titular;Valor;TipoTransacao;Data";
            writer.write(cabecalho);
            writer.newLine();
            
            for (Transacao transacao : transacoes) {
                String concat;
                if (transacao instanceof Receitas) {
                    Receitas receita = (Receitas) transacao;
                    concat = receita.getTipoReceita() + ";" + receita.getDescricao() + ";"
                            + receita.getValor() + ";" + receita.getTipoTransacao()
                            + ";" + receita.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                } else if (transacao instanceof Despesas) {
                    Despesas despesa = (Despesas) transacao;
                    concat = despesa.getTipoDespesa() + ";" + despesa.getDescricao() + ";"
                            + despesa.getValor() + ";" + despesa.getTipoTransacao()
                            + ";" + despesa.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                } else {
                    concat = "";
                }
                writer.write(concat);
                writer.newLine();
            }
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(ExportadorArquivo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ExportadorArquivo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
