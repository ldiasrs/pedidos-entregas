package com.aceleradora.pedidosentregas.service;

import com.aceleradora.pedidosentregas.client.EmailSenderClient;
import com.aceleradora.pedidosentregas.model.email.EmailRequest;
import com.aceleradora.pedidosentregas.model.email.EmailResponse;
import com.aceleradora.pedidosentregas.model.pedido.PedidoEntrega;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {


    private EmailSenderClient emailSenderClient;

    public EmailSender(@Qualifier("gmail") EmailSenderClient emailSenderClient) {
        this.emailSenderClient = emailSenderClient;
    }

    public EmailResponse sendEmail(String emailTo, PedidoEntrega pedidoEntrega) {
        return emailSenderClient.sendEmail(
                EmailRequest.builder()
                        .text(pedidoEntrega.toString())
                        .from("pedidos-entrega@gmail.com")
                        .to(emailTo)
                        .subject("Novo pedido de entrega para: " + pedidoEntrega.getDataHoraBuscaPacoteOrigem().toString())
                        .build()
        );
    }
}
