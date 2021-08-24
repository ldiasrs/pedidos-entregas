package com.aceleradora.pedidosentregas.exception;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class ErrorModel {

    private final String error;
    private final String message;
    private final String additionalDetails;
    private final String service;
}
