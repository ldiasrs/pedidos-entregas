package com.aceleradora.pedidosentregas.model.pedido;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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


}
