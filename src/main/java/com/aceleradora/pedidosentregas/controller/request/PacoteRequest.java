package com.aceleradora.pedidosentregas.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PacoteRequest {
    private float pesoPacoteEmGramas;
    private float larguraEmCentimetros;
    private float alturaEmCentimetros;
    private float profundidadeEmCentimetros;
}
