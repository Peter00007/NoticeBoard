package com.board.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class MailerUtil {
    public static void send(String to, String subject, String msg) {
        try {
            Properties properties = new Properties();

            String dbSettingsPropertyFile = "C:\\Users\\Acer\\Desktop\\NoticeBoard\\src\\main\\resources\\mail.properties";
            FileReader fileReader = new FileReader(dbSettingsPropertyFile);
            properties.load(fileReader);
            String host = properties.getProperty("mail.smtps.host");
            String port = properties.getProperty("mail.smtp.port");
            String user = properties.getProperty("mail.smtps.user");
            String password = properties.getProperty("mail.smtps.password");
            String starttlsEnable = properties.getProperty("mail.smtp.starttls.enable");
            String smtpsAuth = properties.getProperty("mail.smtps.auth");

            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);
            properties.put("mail.from", user);
            properties.put("mail.smtp.password", password);
            properties.put("mail.smtp.auth", smtpsAuth);
            properties.put("mail.smtp.starttls.enable", starttlsEnable);

            Session session = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(user, password);
                        }
                    });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            message.setSubject(subject);
            message.setText(msg);
            Transport.send(message);

        } catch (FileNotFoundException | AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


