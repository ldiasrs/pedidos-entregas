package com.aceleradora.pedidosentregas.controller;

import com.aceleradora.pedidosentregas.controller.request.PedidoEntregaRequest;
import com.aceleradora.pedidosentregas.exception.PedidoNotFoundException;
import com.aceleradora.pedidosentregas.model.pedido.*;
import com.aceleradora.pedidosentregas.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidoentrega")
public class PedidosEntregaController {

    private PedidoService pedidoService;

    public PedidosEntregaController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<PedidoEntregaResponse> create(@RequestBody PedidoEntregaRequest pedidoEntregaRequest) {
        var pedido = pedidoService.register(pedidoEntregaRequest);
        return ResponseEntity.ok(new PedidoEntregaResponse("Pedido registrado com sucesso", pedido.getId()));
    }

    @GetMapping(path = "/{{id}}")
    public ResponseEntity<PedidoEntrega> getById(@PathVariable("id") int id) {
        PedidoEntrega pedido = pedidoService.findPedidoById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido nao encontrado com ID: " + id));
        return ResponseEntity.ok(pedido);
    }
}
