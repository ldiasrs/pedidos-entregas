package com.aceleradora.pedidosentregas.model;

import com.aceleradora.pedidosentregas.model.pedido.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;

class PedidoEntregaTest {

    @Test
    public void deveCriarUmPedidoDeEntregaComSucesso() {
        var pedido = PedidoEntrega.builder()
                .pacote(createPacote())
                .localOrigem(createLocalOrigem())
                .localOrigem(createLocalOrigem())
                .localDestino(createLocalDestino())
                .dataHoraBuscaPacoteOrigem(
                        of(2020, 10, 2, 9, 0))
                .valorDaEntrega(BigDecimal.valueOf(15))
                .observacoes("Pode ser entregue na portaria")
                .build();

        //TODO Esses asserts poderiam ser mais especficos nao acha?
        assertThat(pedido.getPacote()).isNotNull();
        assertThat(pedido.getLocalOrigem()).isNotNull();
        assertThat(pedido.getLocalDestino()).isNotNull();
        assertThat(pedido.getDataHoraBuscaPacoteOrigem()).isNotNull();
        assertThat(pedido.getValorDaEntrega()).isNotNull();
        assertThat(pedido.getObservacoes()).isNotNull();
    }

    private Local createLocalDestino() {
        return Local.builder()
                .contato(
                        Contato.builder()
                                .nome("Gandolf")
                                .telefone("10")
                                .build())
                .endereco(
                        Endereco.builder()
                                .cep("000000001")
                                .numero("1011")
                                .complemento("apt 1001")
                                .build()
                )
                .build();
    }

    private Local createLocalOrigem() {
        return Local.builder()
                .contato(
                        Contato.builder()
                                .nome("Saruman")
                                .telefone("171")
                                .build())
                .endereco(
                        Endereco.builder()
                                .cep("000000002")
                                .numero("2171")
                                .complemento("apt 901")
                                .build()
                )
                .build();
    }

    private Pacote createPacote() {
        return Pacote.builder()
                .alturaEmCentimetros(10)
                .profundidadeEmCentimetros(10)
                .larguraEmCentimetros(20)
                .pesoPacoteEmGramas(2000)
                .build();
    }

}
