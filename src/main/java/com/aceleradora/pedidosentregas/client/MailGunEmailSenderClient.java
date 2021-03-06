package com.aceleradora.pedidosentregas.client;

import com.aceleradora.pedidosentregas.model.email.EmailRequest;
import com.aceleradora.pedidosentregas.model.email.EmailResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component("mailgun")
public class MailGunEmailSenderClient implements  EmailSenderClient {

    public static final String MAILGUN_DOMAIN_TAG = "<MAILGUN_DOMAIN>";
    private String apiKey;
    private String url;

    public MailGunEmailSenderClient(@Value("${mailgun.apikey}") String apiKey,
                                    @Value("${mailgun.urlTemplate}") String urlTemplate) {
        this.apiKey = apiKey;
        this.url = urlTemplate;
    }

    @Override
    public EmailResponse sendEmail(EmailRequest email) {
        HttpResponse<JsonNode> request = Unirest.post(url)
                .basicAuth("api", apiKey)
                .field("from", email.getFrom())
                .field("to", email.getTo())
                .field("subject", email.getSubject())
                .field("text", email.getText())
                .asJson();

        return EmailResponse.builder()
                .message(request.getBody().toPrettyString())
                .build();
    }
}

