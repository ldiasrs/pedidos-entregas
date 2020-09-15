package com.aceleradora.pedidosentregas.model.pedido;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Contato {
    private String nome;
    private String telefone;
}
