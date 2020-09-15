package com.aceleradora.pedidosentregas.controller;

import com.aceleradora.pedidosentregas.model.pedido.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class PedidosEntregaController {

    @GetMapping(path = "/sendEmail")
    public ResponseEntity<PedidoEntrega> defaultPedido() {
        var pedido = PedidoEntrega.builder()
                .observacoes("Pode entregar para o porteiro")
                .valorDaEntrega(BigDecimal.valueOf(20))
                .localOrigem(Local.builder()
                        .contato(Contato.builder()
                                .nome("Gandolf")
                                .telefone("1010")
                                .build())
                        .endereco(Endereco.builder()
                                .complemento("apt 555")
                                .cep("0000001")
                                .build())
                        .build())
                .pacote(Pacote.builder()
                        .larguraEmCentimetros(10)
                        .profundidadeEmCentimetros(20)
                        .alturaEmCentimetros(5)
                        .build())
                .build();
         return ResponseEntity.ok(pedido);
    }
}
