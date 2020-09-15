package com.aceleradora.pedidosentregas.model.pedido;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Pacote {

    private float pesoPacoteEmGramas;
    private int larguraEmCentimetros;
    private int alturaEmCentimetros;
    private int profundidadeEmCentimetros;

}
