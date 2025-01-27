package com.itma.gestionProjet.utils;

import com.itma.gestionProjet.dtos.MailRequestDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    
    @Value("${spring.mail.username}")
    private String fromMail;
    
    @Value("${app.support.mail}")
    private String emailSupport;
    

    private MimeMessage mail(MailRequestDto request) throws MessagingException {
        log.info("Sending email {}", request.getToEmail());
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom(fromMail);
        mimeMessageHelper.setTo(request.getToEmail());
        mimeMessageHelper.setSubject(request.getSubject());


        Context context = new Context();
        /*
        content is the variable defined in our HTML template within the div tag
        */
        context.setVariable("content", request.getMessage());
        context.setVariable("emailSupport", emailSupport);
        String processedString = templateEngine.process(request.getTemplate(), context);

        mimeMessageHelper.setText(processedString, true);
        return mimeMessage;
    }



    @Async
    public void sendMail(MailRequestDto request) throws MessagingException {

        mailSender.send(mail(request));
        log.info("Email sent");
    }


    @Async
    public void sendMailReception(MailRequestDto request) throws MessagingException {
        mailSender.send(mail(request));
    }
}
