package com.aceleradora.pedidosentregas.repository;

import com.aceleradora.pedidosentregas.model.pedido.PedidoEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoEntregaRepository extends JpaRepository<PedidoEntrega, Integer> {
}
