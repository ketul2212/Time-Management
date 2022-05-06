package com.ketul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMail(String toMail, String token) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("pketul2212@gmail.com");
        mailMessage.setTo(toMail);
        mailMessage.setText("Conform Your Mail with the below link\n"
                + "http://localhost:8100/conform/" + token);
        mailMessage.setSubject("Conformation Mail");

        mailSender.send(mailMessage);
    }
}