package com.aceleradora.pedidosentregas.model.email;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class EmailRequest {
    private String to;
    private String from;
    private String subject;
    private String text;
}
