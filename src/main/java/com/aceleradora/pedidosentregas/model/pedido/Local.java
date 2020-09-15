package com.aceleradora.pedidosentregas.model.pedido;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Local {
    private Contato contato;
    private Endereco endereco;
}
