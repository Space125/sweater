package org.example.sweater.service;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Ivan Kurilov on 26.04.2021
 */

@Service
public class MailSenderService {

    private final String username;

    private final JavaMailSender mailSender;

    public MailSenderService(MailProperties mailProperties,
                             JavaMailSender mailSender) {
        this.username = mailProperties.getUsername();
        this.mailSender = mailSender;
    }

    public void send(String emailTo, String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);


        mailSender.send(mailMessage);

    }
}
