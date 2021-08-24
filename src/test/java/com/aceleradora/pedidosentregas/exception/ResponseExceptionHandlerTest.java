package com.aceleradora.pedidosentregas.exception;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseExceptionHandlerTest {

    private ResponseExceptionHandler exceptionHandler;

    @BeforeEach
    public void setup() {
        exceptionHandler = new ResponseExceptionHandler();
    }

    @Test
    public void shouldHandlePedidoNotFoundException() {
        String mensagemEsperada = "Mensagem esperada";
        PedidoNotFoundException pedidoNotFoundException = new PedidoNotFoundException(mensagemEsperada);

        ResponseEntity<ErrorModel> response = exceptionHandler.handlePedidoNotFound(pedidoNotFoundException);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(response.getBody().getMessage()).contains(mensagemEsperada);
    }

    @Test
    public void shouldHandleException() {
        String mensagemEsperada = "Mensagem esperada";
        Exception exception = new Exception(mensagemEsperada);

        ResponseEntity<ErrorModel> response = exceptionHandler.handleGenericFailure(exception);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        Assertions.assertThat(response.getBody().getMessage()).contains(mensagemEsperada);
    }

}
