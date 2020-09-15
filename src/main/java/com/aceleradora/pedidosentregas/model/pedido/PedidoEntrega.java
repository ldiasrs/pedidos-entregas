package com.aceleradora.pedidosentregas.model.pedido;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Value
public class PedidoEntrega {

    private String observacoes;
    private Pacote pacote;
    private Local localOrigem;
    private Local localDestino;
    private LocalDateTime dataHoraBuscaPacoteOrigem;
    private BigDecimal valorDaEntrega;
}
