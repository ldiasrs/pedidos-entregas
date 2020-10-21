package com.aceleradora.pedidosentregas.service;

import com.aceleradora.pedidosentregas.controller.request.PedidoEntregaRequest;
import com.aceleradora.pedidosentregas.model.pedido.*;
import com.aceleradora.pedidosentregas.repository.PedidoEntregaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PedidoService {

    private PedidoEntregaRepository pedidoEntregaRepository;

    public PedidoService(PedidoEntregaRepository pedidoEntregaRepository) {
        this.pedidoEntregaRepository = pedidoEntregaRepository;
    }

    public Optional<PedidoEntrega> findPedidoById(int id) {
        return pedidoEntregaRepository.findById(id);
    }

    public PedidoEntrega register(PedidoEntregaRequest pedidoEntregaRequest) {
        var pedido = PedidoEntrega.from(pedidoEntregaRequest);
       return pedidoEntregaRepository.save(pedido);
    }
}
