package model;

import org.junit.Before;
import org.junit.Test;

import model.Despesas;
import model.ExportadorArquivo;
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

public class ExportadorArquivoTest {

    private ExportadorArquivo exportador;
    private ArrayList<Transacao> transacoes;

    @Before
    public void setUp() {
        exportador = new ExportadorArquivo();
        transacoes = new ArrayList<>();

        // Setup a temporary file for testing
        File tempFile;
        try {
            tempFile = File.createTempFile("test", ".csv");
            exportador.setFile(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add some test transactions
        transacoes.add(new Receitas(TipoReceita.Salario, "Salário de Junho", 3000.0, TipoTransacao.Receita, LocalDate.of(2024, 6, 5)));
        transacoes.add(new Despesas(TipoDespesa.Alimentacao, "Supermercado", 200.0, TipoTransacao.Despesa, LocalDate.of(2024, 6, 6)));
    }

    @Test
    public void testGravarArquivo() {
        exportador.gravarArquivo(transacoes);

        // Read the file and check its content
        try (BufferedReader reader = new BufferedReader(new FileReader(exportador.getFile()))) {
            String header = reader.readLine();
            assertEquals("TipoReceita;Titular;Valor;TipoTransacao;Data", header);

            String receitaLine = reader.readLine();
            assertEquals("Salario;Salário de Junho;3000.0;Receita;05-06-2024", receitaLine);

            String despesaLine = reader.readLine();
            assertEquals("Alimentacao;Supermercado;200.0;Despesa;06-06-2024", despesaLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetSetFile() {
        File newFile = new File("new_test_file.csv");
        exportador.setFile(newFile);
        assertEquals(newFile, exportador.getFile());
    }

    @Test
    public void testFileNotNull() {
        assertTrue(exportador.getFile() != null);
    }
}
