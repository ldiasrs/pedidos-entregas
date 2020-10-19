package com.aceleradora.pedidosentregas.model.pedido;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "endereco")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Endereco {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String cep;
    private String numero;
    private String complemento;

}
