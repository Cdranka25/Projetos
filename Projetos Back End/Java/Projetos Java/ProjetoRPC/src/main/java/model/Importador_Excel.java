/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class Importador_Excel {

    private String filePath;

    public Importador_Excel(String filePath) {
        this.filePath = filePath;
    }

    public List<ClientData> importData() throws IOException {
        List<ClientData> clients = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Ignorar o cabeçalho
                }

                // Verifica se as células possuem valor antes de tentar acessá-las
                String observacao = getStringCellValue(row, 1);
                String empresa = getStringCellValue(row, 2);
                String cnpj = getStringCellValue(row, 3);
                String num_Contrato = getStringCellValue(row, 4);
                int dia = getIntCellValue(row, 5);
                int mes = getIntCellValue(row, 6);
                int ano = getIntCellValue(row, 7);
                String tipo = getStringCellValue(row, 8);
                String formaPagamento = getStringCellValue(row, 9);
                double valor = getDoubleCellValue(row, 10);
                String produto = getStringCellValue(row, 11);
                String email = getStringCellValue(row, 12);
                double valorTotalAnual = getDoubleCellValue(row, 13);
                int anoContrato = getIntCellValue(row, 14);

                clients.add(new ClientData(observacao, empresa, cnpj, num_Contrato, dia, mes, ano, tipo, formaPagamento, valor, produto, email, valorTotalAnual, anoContrato));
            }
        }

        return clients;
    }

    // Métodos auxiliares para obter os valores das células de forma segura
    private String getStringCellValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        return (cell != null && cell.getCellType() == CellType.STRING) ? cell.getStringCellValue() : "";
    }

    private int getIntCellValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        return (cell != null && cell.getCellType() == CellType.NUMERIC) ? (int) cell.getNumericCellValue() : 0;
    }

    private double getDoubleCellValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        return (cell != null && cell.getCellType() == CellType.NUMERIC) ? cell.getNumericCellValue() : 0.0;
    }
}
