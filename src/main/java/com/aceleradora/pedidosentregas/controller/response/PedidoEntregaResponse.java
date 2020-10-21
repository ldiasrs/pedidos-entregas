package com.aceleradora.pedidosentregas.controller.response;

import com.aceleradora.pedidosentregas.model.pedido.PedidoEntrega;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PedidoEntregaResponse {
    public static final String MSG_PEDIDO_REGISTRADO_COM_SUCESSO = "Pedido registrado com sucesso";
    private String msg;
    private long codigo;

    public static PedidoEntregaResponse from(PedidoEntrega pedidoEntrega) {
        return PedidoEntregaResponse.builder()
                .codigo(pedidoEntrega.getId())
                .msg(MSG_PEDIDO_REGISTRADO_COM_SUCESSO)
                .build();
    }
}
