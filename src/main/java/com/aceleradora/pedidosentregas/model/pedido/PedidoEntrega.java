package com.aceleradora.pedidosentregas.model.pedido;

import com.aceleradora.pedidosentregas.controller.request.PedidoEntregaRequest;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "pedido_entrega")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PedidoEntrega {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String observacoes;
    @OneToOne(cascade=CascadeType.ALL)
    private Pacote pacote;
    @OneToOne(cascade=CascadeType.ALL)
    private Local localOrigem;
    @OneToOne(cascade=CascadeType.ALL)
    private Local localDestino;
    private LocalDateTime dataHoraBuscaPacoteOrigem;
    private BigDecimal valorDaEntrega;


    public static PedidoEntrega from(PedidoEntregaRequest request) {
        return PedidoEntrega.builder()
                .observacoes(request.getObservacoes())
                .dataHoraBuscaPacoteOrigem(LocalDateTime.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").parse(request.getDataHoraBuscaPacoteOrigem())))
                .valorDaEntrega(request.getValorDaEntrega())
                .pacote(Pacote.builder()
                        .alturaEmCentimetros(request.getPacote().getAlturaEmCentimetros())
                        .larguraEmCentimetros(request.getPacote().getLarguraEmCentimetros())
                        .pesoPacoteEmGramas(request.getPacote().getPesoPacoteEmGramas())
                        .profundidadeEmCentimetros(request.getPacote().getProfundidadeEmCentimetros())
                        .build()
                )
                .localOrigem(Local.builder()
                        .contato(Contato.builder()
                                .nome(request.getLocalOrigem().getContato().getNome())
                                .telefone(request.getLocalOrigem().getContato().getTelefone())
                                .build())
                        .endereco(Endereco.builder()
                                .cep(request.getLocalOrigem().getEndereco().getCep())
                                .numero(request.getLocalOrigem().getEndereco().getNumero())
                                .complemento(request.getLocalOrigem().getEndereco().getComplemento())
                                .build())
                        .build()
                )
                .localDestino(Local.builder()
                        .contato(Contato.builder()
                                .nome(request.getLocalDestino().getContato().getNome())
                                .telefone(request.getLocalDestino().getContato().getTelefone())
                                .build()
                        )
                        .endereco(Endereco.builder()
                                .cep(request.getLocalDestino().getEndereco().getCep())
                                .numero(request.getLocalDestino().getEndereco().getNumero())
                                .complemento(request.getLocalDestino().getEndereco().getComplemento())
                                .build())
                        .build())
                .build();
    }

}
