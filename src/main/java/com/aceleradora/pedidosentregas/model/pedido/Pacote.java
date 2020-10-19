package com.aceleradora.pedidosentregas.model.pedido;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "pacote")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pacote {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private float pesoPacoteEmGramas;
    private int larguraEmCentimetros;
    private int alturaEmCentimetros;
    private int profundidadeEmCentimetros;

}
