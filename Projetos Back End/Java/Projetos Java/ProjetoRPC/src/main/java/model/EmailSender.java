/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

/**
 *
 * @author user
 */
public class EmailSender {

    private String senderEmail;
    private String password;

    public EmailSender(String senderEmail, String password) {
        this.senderEmail = senderEmail;
        this.password = password;
    }

    public void sendEmail(String recipient, String subject, String body) {

        if (!isValidEmail(recipient)) {
            System.err.println("Endereço de e-mail inválido: " + recipient);
            return;
        }
        Properties props = new Properties();
        // props.put("mail.smtp.auth", "true");
        // props.put("mail.smtp.starttls.enable", "true");
        // props.put("mail.smtp.host", "smtp.gmail.com");
        //props.put("mail.smtp.port", "587");

        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);                      // Cria a mensagem de e-mail
            message.setFrom(new InternetAddress(senderEmail));               // Define o remetente
            message.setRecipients(Message.RecipientType.TO, // Define o destinatário
                    InternetAddress.parse(recipient));
            message.setSubject(subject);                                     // Define o assunto do e-mail
            message.setText(body);                                           // Define o corpo do e-mail

            Transport.send(message);                                         // Envia o e-mail
            System.out.println("Email enviado para: " + recipient);         // Confirmação de envio
        } catch (MessagingException e) {
            System.err.println("Erro ao enviar e-mail para " + recipient + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}
