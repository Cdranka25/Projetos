/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author user
 */
public class ClientData {

    private String observacao;
    private String empresa;
    private String cnpj;
    private String num_Contrato;
    private int dia, mes, ano;
    private String tipo;
    private String formaPagamento;
    private double valor;
    private String produto;
    private String email;
    private double valorTotalAnual;
    private int anoContrato;

    public ClientData(String observacao, String empresa, String cnpj, String num_Contrato, int dia, int mes, int ano, String tipo, String formaPagamento, double valor, String produto, String email, double valorTotalAnual, int anoContrato) {
        this.observacao = observacao;
        this.empresa = empresa;
        this.cnpj = cnpj;
        this.num_Contrato = num_Contrato;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.tipo = tipo;
        this.formaPagamento = formaPagamento;
        this.valor = valor;
        this.produto = produto;
        this.email = email;
        this.valorTotalAnual = valorTotalAnual;
        this.anoContrato = anoContrato;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNum_Contrato() {
        return num_Contrato;
    }

    public void setNum_Contrato(String num_Contrato) {
        this.num_Contrato = num_Contrato;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getValorTotalAnual() {
        return valorTotalAnual;
    }

    public void setValorTotalAnual(double valorTotalAnual) {
        this.valorTotalAnual = valorTotalAnual;
    }

    public int getAnoContrato() {
        return anoContrato;
    }

    public void setAnoContrato(int anoContrato) {
        this.anoContrato = anoContrato;
    }

    public String toString() {
        return "ClientData{"
                + "empresa='" + empresa + '\''
                + ", cnpj='" + cnpj + '\''
                + ", num_Contrato='" + num_Contrato + '\''
                + ", dia=" + dia
                + ", mes=" + mes
                + ", ano=" + ano
                + ", tipo='" + tipo + '\''
                + ", formaPagamento='" + formaPagamento + '\''
                + ", valor=" + valor
                + ", produto='" + produto + '\''
                + ", email='" + email + '\''
                + ", valorTotalAnual=" + valorTotalAnual
                + ", anoContrato=" + anoContrato
                + '}';
    }
}
