package com.aceleradora.pedidosentregas.controller;

import com.aceleradora.pedidosentregas.controller.request.PedidoEntregaRequest;
import com.aceleradora.pedidosentregas.controller.response.PedidoEntregaResponse;
import com.aceleradora.pedidosentregas.exception.PedidoNotFoundException;
import com.aceleradora.pedidosentregas.model.pedido.*;
import com.aceleradora.pedidosentregas.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/pedidoentrega")
public class PedidosEntregaController {

    private PedidoService pedidoService;

    public PedidosEntregaController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<PedidoEntregaResponse> create(@Valid  @RequestBody PedidoEntregaRequest pedidoEntregaRequest) {
        var pedido = pedidoService.register(pedidoEntregaRequest);
        return ResponseEntity.ok(PedidoEntregaResponse.from(pedido));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PedidoEntrega> getById(@PathVariable("id") int id) {
        PedidoEntrega pedido = pedidoService.findPedidoById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido nao encontrado com ID: " + id));
        return ResponseEntity.ok(pedido);
    }

    @GetMapping(path = "/info")
    public ResponseEntity<String> getInfo() {
        return ResponseEntity.ok("Info: Este é um mapeamento que nao requer autenticação");
    }


    @GetMapping(path = "/admin")
    public ResponseEntity<String> getAdmin() {
        return ResponseEntity.ok("Info: Este mapeamento só é acessado com credencial de Autoritie: ADMIN");
    }
}
