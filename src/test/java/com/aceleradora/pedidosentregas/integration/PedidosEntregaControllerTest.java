package com.aceleradora.pedidosentregas.integration;

import com.aceleradora.pedidosentregas.controller.PedidosEntregaController;
import com.aceleradora.pedidosentregas.controller.request.PedidoEntregaRequest;
import com.aceleradora.pedidosentregas.controller.response.PedidoEntregaResponse;
import com.aceleradora.pedidosentregas.model.pedido.Contato;
import com.aceleradora.pedidosentregas.model.pedido.Local;
import com.aceleradora.pedidosentregas.model.pedido.PedidoEntrega;
import com.aceleradora.pedidosentregas.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.aceleradora.pedidosentregas.controller.PathMappings.*;
import static com.aceleradora.pedidosentregas.controller.ValidRequestBuilder.newPedidoEntregaRequest;
import static com.aceleradora.pedidosentregas.controller.response.PedidoEntregaResponse.MSG_PEDIDO_REGISTRADO_COM_SUCESSO;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Disabled
@ExtendWith(SpringExtension.class)
@WebMvcTest(PedidosEntregaController.class)
class PedidosEntregaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @WithMockUser(value = "spring")
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
        //DADO que o pedido sera retornado pelo servico
        when(pedidoService.findPedidoById(expectedPedido.getId()))
                .thenReturn(
                        Optional.of(expectedPedido)
                );

        //QUANDO for solicitado esse pedido via HTTP
        //ENTAO deve retornar 200 OK com o JSON do pedido
        String expecteJsonContent = new ObjectMapper().writeValueAsString(expectedPedido);
        this.mockMvc.perform(get(BASE_PATH_MAPPING+"/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expecteJsonContent));
    }

    @WithMockUser(value = "spring")
    @Test
    public void deveRegistrarPedidoDeEntregaComSucesso() throws Exception {
        //DADO um pedido request sendo regsitrado pelo servico com sucesso
        PedidoEntregaRequest pedidoEntregaRequest = newPedidoEntregaRequest();
        PedidoEntrega expectedPedido =
                PedidoEntrega.builder()
                        .id(1)
                        .build();
        //DADO que o servico retorna o ID desse pedido
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
                post(getFullPath(CREATE_MAPPING))
                .contentType(APPLICATION_JSON)
                .content(jsonPedidoEntregaRequestContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expecteJsonResponseContent));
    }

}
