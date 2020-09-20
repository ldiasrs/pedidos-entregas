package com.aceleradora.pedidosentregas.client;

import com.aceleradora.pedidosentregas.model.email.EmailRequest;
import com.aceleradora.pedidosentregas.model.email.EmailResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class GmailEmailSenderClientTest {

    @Autowired
    private GmailEmailSenderClient client;

    @Test
    public void shouldSendEmail() {
        EmailResponse response = client.sendEmail(
                EmailRequest.builder()
                        .to("ldias.rs@gmail.com")
                        .from("??")
                        .subject("Teste email GMAIL")
                        .text("Aqui vai o corpo do email")
                        .build()
        );
        assertThat(response).isNotNull();
        System.out.println(response.toString());
    }

}
