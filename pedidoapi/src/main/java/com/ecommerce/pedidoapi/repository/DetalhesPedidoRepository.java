package com.ecommerce.pedidoapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.pedidoapi.model.DetalhesPedidoModel;

public interface DetalhesPedidoRepository extends JpaRepository<DetalhesPedidoModel, Long> {
	
	@Query(value = "select * from detalhes_pedido where id_detalhes_pedido = :idDetalesPedido", nativeQuery = true)
	Optional<DetalhesPedidoModel> findById(Long id);
	
	@Query(value = "select * from detalhes_pedido where id_pedido = :idPedido", nativeQuery = true)
	public List<DetalhesPedidoModel> findDetalhesPedidoByPedido(@Param(value = "idPedido") Long idPedido);
	
}
