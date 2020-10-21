package com.aceleradora.pedidosentregas.controller;

import com.aceleradora.pedidosentregas.controller.request.PedidoEntregaRequest;
import com.aceleradora.pedidosentregas.controller.response.PedidoEntregaResponse;
import com.aceleradora.pedidosentregas.model.pedido.Contato;
import com.aceleradora.pedidosentregas.model.pedido.Local;
import com.aceleradora.pedidosentregas.model.pedido.PedidoEntrega;
import com.aceleradora.pedidosentregas.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.aceleradora.pedidosentregas.controller.ValidRequestBuilder.newPedidoEntregaRequest;
import static com.aceleradora.pedidosentregas.controller.response.PedidoEntregaResponse.MSG_PEDIDO_REGISTRADO_COM_SUCESSO;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PedidosEntregaController.class)
class PedidosEntregaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Test
    public void deveRetornarPedidoDeEntregaPorID() throws Exception {
        //DADO um pedido sendo retornado pelo servico
        PedidoEntrega expectedPedido =
                PedidoEntrega.builder()
                        .id(1)
                        .localDestino(Local.builder()
                                .contato(Contato.builder()
                                        .nome("Gandolf")
                                        .build())
                                .build())
                        .build();
        when(pedidoService.findPedidoById(expectedPedido.getId()))
                .thenReturn(
                        Optional.of(expectedPedido)
                );

        //QUANDO for solicitado esse pedido via HTTP
        //ENTAO deve retornar 200 OK com o JSON do pedido
        String expecteJsonContent = new ObjectMapper().writeValueAsString(expectedPedido);
        this.mockMvc.perform(get("/api/pedidoentrega/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expecteJsonContent));
    }

    @Test
    public void deveRegistrarPedidoDeEntregaComSucesso() throws Exception {
        //DADO um pedido request sendo regsitrado pelo servico com sucesso
        //DADO que o servico retorna o ID desse pedido
        PedidoEntregaRequest pedidoEntregaRequest = newPedidoEntregaRequest();
        PedidoEntrega expectedPedido =
                PedidoEntrega.builder()
                        .id(1)
                        .build();
        when(pedidoService.register(pedidoEntregaRequest))
                .thenReturn(
                        expectedPedido
                );

        //QUANDO for solicitado para registrar esse pedido via HTTP
        //ENTAO deve retornar 200 OK com o JSON do pedido resposta e ID
        var pedidoResponse = PedidoEntregaResponse.builder()
                .codigo(expectedPedido.getId())
                .msg(MSG_PEDIDO_REGISTRADO_COM_SUCESSO)
                .build();
        String expecteJsonResponseContent = new ObjectMapper()
                .writeValueAsString(pedidoResponse);
        String jsonPedidoEntregaRequestContent = new ObjectMapper()
                .writeValueAsString(pedidoEntregaRequest);
        this.mockMvc.perform(
                post("/api/pedidoentrega/create")
                .contentType(APPLICATION_JSON)
                .content(jsonPedidoEntregaRequestContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expecteJsonResponseContent));
    }

}
