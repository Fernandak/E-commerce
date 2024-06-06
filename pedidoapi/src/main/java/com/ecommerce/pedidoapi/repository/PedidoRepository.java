package com.ecommerce.pedidoapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.pedidoapi.model.PedidoModel;

import jakarta.transaction.Transactional;
@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {

	@Query(value = "UPDATE pedido SET status_pedido =5 WHERE id_pedido = :id", nativeQuery = true)
	@Modifying
	@Transactional
	public void deletePedido(@Param("id") Long id);

	@Query(value = "select * from pedido where status_pedido = :statusPedido and id_usuario = :id", nativeQuery = true)
	public List<PedidoModel> findAllByStatus(@Param(value = "id") Long id,	@Param(value = "statusPedido") Integer statusPedido);

//	@Query(value = "select * from pedido, detalhes_pedido, produto where id_pedido = :idPedido and id_pedido = :idPedido and id_pedido = :idPedido", nativeQuery = true)
//	public List<PedidoModel> findAll(@Param(value = "id") Long id);
	
	@Query(value = "select * from pedido  where id_usuario = :idUsuario", nativeQuery = true)
	public List<PedidoModel> findAllByUsuario(@Param(value = "idUsuario") Long idUsuario);
	
}