package com.aceleradora.pedidosentregas.client;

import com.aceleradora.pedidosentregas.model.email.EmailRequest;
import com.aceleradora.pedidosentregas.model.email.EmailResponse;
import org.junit.jupiter.api.Test;

import static com.aceleradora.pedidosentregas.client.MailGunEmailSenderClient.MAILGUN_DOMAIN_TAG;
import static org.assertj.core.api.Assertions.assertThat;

class MailGunEmailSenderClientTest {

    @Test
    public void shouldSendEmail() {
        String urlTemplate = "https://api.mailgun.net/v3/" + MAILGUN_DOMAIN_TAG + "/messages";
        String apiKey = "<>";
        String domain = "<>";
        MailGunEmailSenderClient client =
                new MailGunEmailSenderClient(apiKey, domain, urlTemplate);
        EmailResponse response = client.sendEmail(
                EmailRequest.builder()
                        .to("??")
                        .from("??")
                        .subject("Teste email")
                        .text("Aqui vai o corpo do email")
                        .build()
        );
        assertThat(response).isNotNull();
        System.out.println(response.toString());
    }
}
