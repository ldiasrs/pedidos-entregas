package com.aceleradora.pedidosentregas.model.pedido;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "local")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Local {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @OneToOne(cascade=CascadeType.ALL)
    private Contato contato;
    @OneToOne(cascade=CascadeType.ALL)
    private Endereco endereco;
}
