package com.api.bigu.services;

import com.api.bigu.exceptions.EmailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;

import java.util.Properties;

@Component
public class JavaMailEmailService implements EmailService{

    @Value("${spring.mail.username}")
    private final String username;
    @Value("${spring.mail.password}")
    private final String password;
    @Value("${spring.mail.host}")
    private final String host;
    @Value("${spring.mail.port}")
    private final int port;

    private final Properties properties;

    public JavaMailEmailService(@Value("${spring.mail.username}") String username,
                                @Value("${spring.mail.password}") String password,
                                @Value("${spring.mail.host}") String host,
                                @Value("${spring.mail.port}") int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
    }

    @Override
    public void sendEmail(String to, String subject, String body) throws MessagingException {
            Session session = Session.getInstance(properties);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message, username, password);
        
    }
}
