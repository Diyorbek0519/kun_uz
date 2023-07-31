package com.example.service;

import com.example.util.JWTUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailHistoryService emailHistoryService;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${server.url}")
    private String serverUrl;
    void sendEmail(String toAccount, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(text);
        javaMailSender.send(msg);
    }
    void sendMimeEmail(String toAccount, String subject, String text) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromEmail);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEmailVerification(String toAccount,String name, Integer id) {
        String jwt = JWTUtil.encodeEmailJwt(id);
        String url = serverUrl + "/api/v1/auth/verification/email/" + jwt;

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("<h1 style=\"text-align: center\">Hello %S</h1>)",name));
        builder.append("<p>");
        builder.append(String.format("<a href=\"%s\"> Click link to complete registration </a>", url));
        builder.append("</p>");
        sendMimeEmail(toAccount, "Kun uz registration compilation", builder.toString());
        emailHistoryService.save(builder.toString(), toAccount);

    }

}
