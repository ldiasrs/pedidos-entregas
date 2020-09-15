package com.aceleradora.pedidosentregas.model.pedido;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Endereco {

    private String cep;
    private String numero;
    private String complemento;

}
