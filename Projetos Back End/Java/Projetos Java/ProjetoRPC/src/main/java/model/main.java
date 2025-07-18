/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author user
 */
public class main {

    public static void main(String[] args) {

        String password = "BPS519200";
        String emailLocal = "suporte3bruspeso@gmail.com";

        String excelPath = "C:\\Users\\user\\Documents\\clientes.xlsx";

        String emailTemplateText = "Bom dia!,\n"
                + "Primeiramente queremos agradecer por esta parceria que só tende a crescer.\n"
                + "Por meio deste, gostaria de informar que seu sistema {produto} {formaPagamento} pelo CPF/CNPJ: {cnpj} vence dia {dia}/{mes}/{ano}. \n"
                + "Visto que a renovação só é realizada mediante aprovação, segue os valores da renovação: "
                + "{formaPagamento} --- {valor} \n\n"
                + "CASO DESEJE ALTERAR SEU PLANO RESPONDA ESTE E-MAIL COM A OPÇÃO DESEJADA, SE DESEJA MANTER O MESMO PLANO APENAS CONFIRME ESTE E-MAIL APROVANDO A RENOVAÇÃO. \n"
                + "Após a confirmação da renovação, realizaremos um acesso remoto ao computador que possui acesso ao sistema, para registrarmos a nova vigência. \n"
                + "O programa de acesso remoto utilizado é o anydesk. Caso ainda não o tenha, podes realizar o download através do link abaixo: https://anydesk.com//pt//downloads//windows \n"
                + "Para uma comunicação mais rápida, conseguem nos chamar em nosso WhatsApp (47) 98439-8093 ou Telefone (47) 3251-9200? \n\n"
                + "Atenciosamente, \n"
                + "Caio D. Dranka";

        String assuntoBase = "RENOVAÇÃO SECULLUM SISTEMA BRUSPESO CONTRATO Nº {num_Contrato} - {empresa}";

        try {
            Importador_Excel importer = new Importador_Excel(excelPath);
            EmailSender sender = new EmailSender(emailLocal, password);

            // Importa dados do Excel
            List<ClientData> clients = importer.importData();

            // Envia os e-mails personalizados
            for (ClientData client : clients) {
                if (client.getEmail() == null || client.getEmail().isEmpty()) {
                    System.err.println("Cliente " + client.getEmpresa() + " não tem e-mail cadastrado.");
                    continue;
                }

                String assuntoPersonalizado = assuntoBase
                        .replace("{num_Contrato}", client.getNum_Contrato())
                        .replace("{empresa}", client.getEmpresa());

                String personalizedMessage = emailTemplateText
                        .replace("{produto}", client.getProduto())
                        .replace("{formaPagamento}", client.getFormaPagamento())
                        .replace("{cnpj}", client.getCnpj())
                        .replace("{dia}", String.valueOf(client.getDia()))
                        .replace("{mes}", String.valueOf(client.getMes()))
                        .replace("{ano}", String.valueOf(client.getAno()));

                sender.sendEmail(client.getEmail(), assuntoPersonalizado, personalizedMessage);

                System.out.println("E-mail enviado para: " + client.getEmpresa() + "-" + client.getEmail() + " - Assunto: " + assuntoPersonalizado);
            }

        } catch (IOException e) {
            System.err.println("Erro ao importar dados do Excel.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mails.");
            e.printStackTrace();
        }
    }
}
