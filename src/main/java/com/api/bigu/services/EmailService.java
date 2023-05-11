package com.api.bigu.services;

import com.api.bigu.exceptions.EmailException;

public interface EmailService {
    void sendEmail(String to, String subject, String body) throws EmailException;
}