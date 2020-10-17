package com.aceleradora.pedidosentregas.model.pedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Value
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contato")
public class Contato implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;
    private String nome;
    private String telefone;
}
