package com.ecommerce.pedidoapi.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.pedidoapi.model.PedidoModel;

import jakarta.validation.Valid;

public interface PedidoService {

	Optional<PedidoModel> registerPedido(@Valid PedidoModel PedidoModel) throws Exception;

	List<PedidoModel> findAll();

	Optional<PedidoModel> findById(Long id);

	void deleteById(Long id);

	List<PedidoModel> findAllByStatus(Long id, Integer statusPedido);

	List<PedidoModel> findAllByUsuario(Long id);

}