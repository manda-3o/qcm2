package com.gestionqcm.service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {
    public void sendEmail(String to, String subject, String content) throws MessagingException {
        String host = System.getenv().getOrDefault("MAIL_HOST", "smtp.gmail.com");
        String port = System.getenv().getOrDefault("MAIL_PORT", "587");
        final String user = System.getenv().getOrDefault("MAIL_USER", "");
        final String pass = System.getenv().getOrDefault("MAIL_PASS", "");
        String from = System.getenv().getOrDefault("MAIL_FROM", user);

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", host);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setContent(content, "text/html; charset=UTF-8");

        Transport.send(message);
    }
}
