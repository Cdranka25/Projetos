/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author user
 */
public class EmailTemplate {

    private String template;

    public EmailTemplate(String template) {
        this.template = template;
    }

    public String personalize(ClientData client) {
        return template
                .replace("{observacao}", client.getObservacao()) // Corrigir para "observacao"
                .replace("{empresa}", client.getEmpresa())
                .replace("{cnpj}", client.getCnpj())
                .replace("{num_Contrato}", client.getNum_Contrato())
                .replace("{dia}", String.valueOf(client.getDia()))
                .replace("{mes}", String.valueOf(client.getMes()))
                .replace("{ano}", String.valueOf(client.getAno()))
                .replace("{tipo}", client.getTipo())
                .replace("{formaPagamento}", client.getFormaPagamento())
                .replace("{valor}", String.format("%.2f", client.getValor()))
                .replace("{produto}", client.getProduto())
                .replace("{email}", client.getEmail())
                .replace("{valorTotalAnual}", String.format("%.2f", client.getValorTotalAnual()))
                .replace("{anoContrato}", String.valueOf(client.getAnoContrato()));
    }
}
