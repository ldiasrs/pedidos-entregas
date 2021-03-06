package com.aceleradora.pedidosentregas.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalRequest {

    @NotNull
    private ContatoRequest contato;
    @NotNull
    private EnderecoRequest endereco;
}
