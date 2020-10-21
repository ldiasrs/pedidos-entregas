package com.aceleradora.pedidosentregas.controller;


import com.aceleradora.pedidosentregas.controller.request.*;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.now;

public final class ValidRequestBuilder {

    private ValidRequestBuilder() {
    }

    public static PedidoEntregaRequest newPedidoEntregaRequest() {
        return  PedidoEntregaRequest.builder()
                .pacote(PacoteRequest.builder()
                        .pesoPacoteEmGramas(50)
                        .alturaEmCentimetros(1)
                        .larguraEmCentimetros(1)
                        .profundidadeEmCentimetros(1)
                        .build())
                .valorDaEntrega(BigDecimal.valueOf(12.5))
                .dataHoraBuscaPacoteOrigem(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").format(now()))
                .observacoes("Use as aguias para entregar mais rapido")
                .localOrigem(
                        LocalRequest.builder()
                                .contato(
                                        ContatoRequest.builder()
                                                .nome("Gandolf")
                                                .telefone("#GreyMage")
                                                .build()
                                )
                                .endereco(EnderecoRequest.builder()
                                        .cep("#Valfenda")
                                        .numero("ElfNumber")
                                        .complemento("Hiden dor on rocks")
                                        .build())
                                .build()
                )
                .localDestino(
                        LocalRequest.builder()
                                .contato(ContatoRequest.builder()
                                        .nome("Sauron")
                                        .telefone("#BigFireEye")
                                        .build())
                                .endereco(
                                        EnderecoRequest.builder()
                                                .cep("#Mordor")
                                                .numero("Vulcano")
                                                .complemento("Hiden dor on vulcano")
                                                .build()
                                )
                                .build()
                )
                .build();
    }
}
