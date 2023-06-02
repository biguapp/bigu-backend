package com.api.bigu.services;

import com.api.bigu.exceptions.EmailException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface EmailService {
    void sendEmail(String to, String subject, String body) throws MessagingException;
}