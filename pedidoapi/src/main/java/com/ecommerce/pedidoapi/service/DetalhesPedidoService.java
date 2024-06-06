package com.ecommerce.pedidoapi.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.pedidoapi.model.DetalhesPedidoModel;

public interface DetalhesPedidoService {
	
	
	List<DetalhesPedidoModel> findAll();
	
	Optional<DetalhesPedidoModel> findById(Long id);

}
