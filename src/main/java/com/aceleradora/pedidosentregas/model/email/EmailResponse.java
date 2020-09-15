package com.aceleradora.pedidosentregas.model.email;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class EmailResponse {
    private String message;
}
