/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.*;
import java.nio.file.*;

/**
 *
 * @author user
 */
public class Gerador_txt {

    private String caminhoArquivo;
    String header = "Tarefa;Prioridade;Categoria;Concluido";

    public Gerador_txt(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        criarArquivo();
    }

    private void criarArquivo() {
        try {
            Path caminho = Paths.get(caminhoArquivo);

            // Cria os diretórios necessários, se não existirem
            if (!Files.exists(caminho.getParent())) {
                Files.createDirectories(caminho.getParent());
                System.out.println("Diretórios criados: " + caminho.getParent().toAbsolutePath());
            }

            // Cria o arquivo, se não existir
            if (!Files.exists(caminho)) {
                Files.createFile(caminho);
                System.out.println("Arquivo criado em: " + caminho.toAbsolutePath());

                // Escreve o cabeçalho no arquivo
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
                    writer.write("Tarefa;Prioridade;Categoria;Concluido");
                    writer.newLine();
                    System.out.println("Cabeçalho escrito com sucesso");
                } catch (IOException e) {
                    System.out.println("Erro ao escrever cabeçalho: " + e.getMessage());
                }
            } else {
                System.out.println("Arquivo já existe em: " + caminho.toAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo: " + e.getMessage());
        }

    }

    //Se append = false, o arquivo é reescrito, apagando o que já existia
    public void escrever(String conteudo, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, append))) {
            writer.write(conteudo);
            writer.newLine();
            System.out.println("Conteúdo escrito no arquivo com sucesso");
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    // Método para ler o arquivo e retornar uma lista de objetos Lista
    public String ler() {
        StringBuilder conteudo = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            // Lê o cabeçalho e o ignora
            String firstLine = reader.readLine();
            if (firstLine != null && !firstLine.equals(header)) {
                System.out.println("Aviso: Cabeçalho diferente do esperado -> " + firstLine);
            }

            // Lê as linhas restantes
            int linhaAtual = 1; // Para controle de erros
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    String[] campos = linha.split(";");

                    // Verifica se a linha tem o número correto de campos
                    if (campos.length != 4) {
                        System.out.println("Linha mal formatada (" + linhaAtual + "): " + linha);
                        continue; // Pula essa linha
                    }

                    try {
                        String tarefa = campos[0].trim();
                        Prioridades prioridade = Prioridades.fromString(campos[1].trim());
                        String categoria = campos[2].trim();
                        boolean concluido = Boolean.parseBoolean(campos[3].trim());

                        System.out.println("Linha " + linhaAtual + " lida com sucesso: " + tarefa + ", " + prioridade + ", " + categoria + ", " + concluido);
                        conteudo.append(linha).append("\n");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro ao processar dados na linha " + linhaAtual + ": " + e.getMessage());
                    }
                }
                linhaAtual++;
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }

        return conteudo.toString();
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

}
