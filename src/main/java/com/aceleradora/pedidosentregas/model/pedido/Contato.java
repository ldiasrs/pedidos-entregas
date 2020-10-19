package com.aceleradora.pedidosentregas.model.pedido;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "contato")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Contato implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String nome;
    private String telefone;
}
