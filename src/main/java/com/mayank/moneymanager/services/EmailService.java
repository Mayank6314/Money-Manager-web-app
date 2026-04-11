package com.mayank.moneymanager.services;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${BREVO_EMAIL}")
    private String fromEmail;

    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true = isHtml
            mailSender.send(message);
            System.out.println("Email sent to: " + to);
        } catch (Exception e) {
            System.out.println("Email failed: " + e.getMessage());
        }
    }

//    public void sendEmail(String to, String subject, String body){
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom(fromEmail);
//            message.setTo(to);
//            message.setSubject(subject);
//            message.setText(body);
//            mailSender.send(message);
//
//        } catch (Exception e) {
//            System.out.println("Email failed" + e.getMessage());
//        }
//    }
}
