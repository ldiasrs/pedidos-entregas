package com.aceleradora.pedidosentregas.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContatoRequest {

    @NotBlank
    private String nome;
    @NotBlank
    private String telefone;
}
