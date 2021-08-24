package com.aceleradora.pedidosentregas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorModel> handleGenericFailure(Exception exception) {
        HttpStatus returnStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorModel responseBody = buildErrorResponse("Internal Server Error",
                "Exception: " + exception.getClass() + "-" + exception.getMessage(), "Pedidos API");
        return new ResponseEntity<>(responseBody, returnStatus);
    }

    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<ErrorModel> handlePedidoNotFound(PedidoNotFoundException exception) {
        HttpStatus returnStatus = HttpStatus.NOT_FOUND;
        ErrorModel responseBody = buildErrorResponse("Pedido not found",
                "Exception: " + exception.getClass() + "-" + exception.getMessage(), "Pedidos API");
        return new ResponseEntity<>(responseBody, returnStatus);
    }

    public ErrorModel buildErrorResponse(String error, String message, String service) {
        return ErrorModel.builder()
                .error(error)
                .additionalDetails("")
                .message(message)
                .service(service)
                .build();
    }
}
