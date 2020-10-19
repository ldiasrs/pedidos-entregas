package com.aceleradora.pedidosentregas.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PedidoEntregaResponse {
    private String msg;
    private long codigo;
}
