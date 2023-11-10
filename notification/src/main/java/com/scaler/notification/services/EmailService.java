package com.scaler.notification.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    @Autowired
    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;

    }

    // Injecting the SMTP_FROM property from the .env file
    @Value("${MAILGUN_MAIL_SMTP_FROM}")
    private String smtpFrom;

    public void sendSimpleEmail(String to, String subject, String body) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(new InternetAddress(smtpFrom, "Grievance Management System"));
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);
        emailSender.send(message);
    }
}



