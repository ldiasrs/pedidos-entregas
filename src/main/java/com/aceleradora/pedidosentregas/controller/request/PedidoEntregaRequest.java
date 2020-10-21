package com.aceleradora.pedidosentregas.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoEntregaRequest {

    private String observacoes;
    @NotNull
    private PacoteRequest pacote;
    @NotNull
    private LocalRequest localOrigem;
    @NotNull
    private LocalRequest localDestino;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String dataHoraBuscaPacoteOrigem;

    @NotNull
    private BigDecimal valorDaEntrega;
}
