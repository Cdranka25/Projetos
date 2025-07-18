package model;

import org.junit.Before;
import org.junit.Test;

import model.Despesas;
import model.ImportadorArquivo;
import model.Receitas;
import model.TipoDespesa;
import model.TipoReceita;
import model.TipoTransacao;
import model.Transacao;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ImportadorArquivoTest {

    private ImportadorArquivo importador;
    private File tempFile;

    @Before
    public void setUp() throws IOException {
        importador = new ImportadorArquivo();

        // Setup a temporary file for testing
        tempFile = File.createTempFile("test", ".csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("TipoReceita;Titular;Valor;TipoTransacao;Data");
            writer.newLine();
            writer.write("Salario;Salário de Junho;3000.0;Receita;05-06-2024");
            writer.newLine();
            writer.write("Alimentacao;Supermercado;200.0;Despesa;06-06-2024");
            writer.newLine();
        }

        importador.setArquivo(tempFile);
    }

    @Test
    public void testProcessarArquivo() {
        importador.processarArquivo();
        ArrayList<Transacao> transacoes = importador.getTransacoes();

        assertEquals(2, transacoes.size());

        Receitas receita = (Receitas) transacoes.get(0);
        assertEquals(TipoReceita.Salario, receita.getTipoReceita());
        assertEquals("Salário de Junho", receita.getDescricao());
        assertEquals(3000.0, receita.getValor(), 0.01);
        assertEquals(TipoTransacao.Receita, receita.getTipoTransacao());
        assertEquals(LocalDate.of(2024, 6, 5), receita.getDate());

        Despesas despesa = (Despesas) transacoes.get(1);
        assertEquals(TipoDespesa.Alimentacao, despesa.getTipoDespesa());
        assertEquals("Supermercado", despesa.getDescricao());
        assertEquals(200.0, despesa.getValor(), 0.01);
        assertEquals(TipoTransacao.Despesa, despesa.getTipoTransacao());
        assertEquals(LocalDate.of(2024, 6, 6), despesa.getDate());
    }

    @Test
    public void testGetSetArquivo() {
        File newFile = new File("new_test_file.csv");
        importador.setArquivo(newFile);
        assertEquals(newFile, importador.getArquivo());
    }

    @Test
    public void testArquivoNotNull() {
        assertTrue(importador.getArquivo() != null);
    }
}
