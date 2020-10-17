package com.aceleradora.pedidosentregas.model.pedido;

import lombok.Builder;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Value
@Entity
public class PedidoEntrega {

    @Id
    private String id;

    private String observacoes;
    private Pacote pacote;
    private Local localOrigem;
    private Local localDestino;
    private LocalDateTime dataHoraBuscaPacoteOrigem;
    private BigDecimal valorDaEntrega;


}
