package com.PSL.PSL.notification;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(
            String to,
            String subject,
            EmailTemplateName templateName,
            Map<String, Object> variables
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );

        Context context = new Context();
        context.setVariables(variables);

        helper.setFrom("PSL@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String content = templateEngine.process(templateName.getName(), context);
        helper.setText(content, true);

        mailSender.send(mimeMessage);

        log.info("Email sent to {}", to);
    }

    // (confirmation ou retard)
    public void sendPharmacyNotification(String email, String pharmacyName, String message) throws MessagingException {
        Map<String, Object> variables = Map.of(
                "username", pharmacyName,
                "message", message
        );
        sendEmail(email, "Confirmation de livraison", EmailTemplateName.PHARMACY_NOTIFICATION, variables); // Utilisation de l'énumération
    }

    //  (problème signalé)
    public void sendGrossisteNotification(String email, String grossisteName, String message) throws MessagingException {
        Map<String, Object> variables = Map.of(
                "username", grossisteName,
                "message", message
        );
        sendEmail(email, "Problème signalé par le livreur", EmailTemplateName.GROSSISTE_NOTIFICATION, variables); // Utilisation de l'énumération
    }
}
