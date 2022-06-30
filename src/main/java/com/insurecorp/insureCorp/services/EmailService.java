package com.insurecorp.insureCorp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendEmail(String toEmail, String subject, String body) throws MessagingException {
//        SimpleMailMessage message = new SimpleMailMessage();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");

        message.setFrom("insurecorp.mailservice@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body, true);
        mailSender.send(mimeMessage);
        System.out.println("Mail Sent Successfully");
    }


}
