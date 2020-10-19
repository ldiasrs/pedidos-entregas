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
        var pedido = PedidoEntrega.builder()
                .observacoes(pedidoEntregaRequest.getObservacoes())
                .valorDaEntrega(BigDecimal.valueOf(20))
                .localOrigem(Local.builder()
                        .contato(Contato.builder()
                                .nome("Gandolf")
                                .telefone("1010")
                                .build())
                        .endereco(Endereco.builder()
                                .complemento("apt 555")
                                .cep("0000001")
                                .build())
                        .build())
                .pacote(Pacote.builder()
                        .larguraEmCentimetros(10)
                        .profundidadeEmCentimetros(20)
                        .alturaEmCentimetros(5)
                        .build())
                .build();
       return pedidoEntregaRepository.save(pedido);
    }
}
