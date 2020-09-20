package com.aceleradora.pedidosentregas.client;

import com.aceleradora.pedidosentregas.model.email.EmailRequest;
import com.aceleradora.pedidosentregas.model.email.EmailResponse;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component("gmail")
public class GmailEmailSenderClient implements EmailSenderClient {

    private JavaMailSender emailSender;

    public GmailEmailSenderClient(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public EmailResponse sendEmail(EmailRequest email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@ldiasrs.com");
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getText());
        emailSender.send(message);
        return EmailResponse.builder()
                .message("Email-sent")
                .build();
    }
}
