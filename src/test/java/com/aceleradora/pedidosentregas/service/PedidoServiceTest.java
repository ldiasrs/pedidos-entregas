package com.aceleradora.pedidosentregas.service;

import com.aceleradora.pedidosentregas.controller.request.PedidoEntregaRequest;
import com.aceleradora.pedidosentregas.model.pedido.PedidoEntrega;
import com.aceleradora.pedidosentregas.repository.PedidoEntregaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;

import java.util.Optional;

import static com.aceleradora.pedidosentregas.controller.ValidRequestBuilder.newPedidoEntregaRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PedidoServiceTest {

    private PedidoService pedidoService;
    private PedidoEntregaRepository mockPedidoRepository;

    @BeforeEach
    public void setup() {
        mockPedidoRepository = mock(PedidoEntregaRepository.class);
        pedidoService = new PedidoService(mockPedidoRepository);
    }

    @Test
    void deveBuscarPedidoComSucesso() {
        //DADO um pedido retornado do repositorio
        PedidoEntrega expectedPedido =
                PedidoEntrega.builder()
                        .id(1)
                        .build();
        when(mockPedidoRepository.findById(expectedPedido.getId()))
                .thenReturn(Optional.of(expectedPedido));

        //QUANDO pedido para buscar esse pedido
        Optional<PedidoEntrega> returnedPedido =
                pedidoService.findPedidoById(expectedPedido.getId());

        //ENTAO a resposta deve ser o proprio pedido
        assertThat(returnedPedido.get()).isEqualTo(expectedPedido);
    }

    @Test
    public void deveRegistrarPedidoComSucesso() {
        //DADO um pedido sendo salvo no repositorio com sucesso
        PedidoEntregaRequest pedidoEntregaRequest = newPedidoEntregaRequest();

        when(mockPedidoRepository.save(any())).thenAnswer(
                (InvocationOnMock invocation) -> invocation.getArguments()[0]);

        //QUANDO pedido para registrar esse pedido
        PedidoEntrega returnedPedido  = pedidoService.register(pedidoEntregaRequest);

        //ENTAO a resposta deve ser um pedidoEntrega com a observacao atualizada
        assertThat(returnedPedido).isNotNull();
        assertThat(returnedPedido.getObservacoes())
                .isEqualTo(pedidoEntregaRequest.getObservacoes());

    }
}
