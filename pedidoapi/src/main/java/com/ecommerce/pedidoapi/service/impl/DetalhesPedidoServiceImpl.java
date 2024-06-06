package com.ecommerce.pedidoapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.pedidoapi.model.DetalhesPedidoModel;
import com.ecommerce.pedidoapi.repository.DetalhesPedidoRepository;
import com.ecommerce.pedidoapi.service.DetalhesPedidoService;

@Service
public class DetalhesPedidoServiceImpl implements DetalhesPedidoService {

	@Autowired
	private DetalhesPedidoRepository detalhesPedidoRepository;

	@Override
	public List<DetalhesPedidoModel> findAll() {
		return detalhesPedidoRepository.findAll();
	}

	@Override
	public Optional<DetalhesPedidoModel> findById(Long id) {
		return detalhesPedidoRepository.findById(id);
	}
}
