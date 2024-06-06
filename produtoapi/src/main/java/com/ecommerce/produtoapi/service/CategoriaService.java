package com.ecommerce.produtoapi.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.produtoapi.model.CategoriaModel;

import jakarta.validation.Valid;

public interface CategoriaService {

	Optional<CategoriaModel> findByIdCategoria(Long id);

	Optional<CategoriaModel> findByNomeCategoria(String nomeCategoria);

	boolean deleteById(long id);

	List<CategoriaModel> findAll();

	Optional<Integer> updateCategoria(Long id, @Valid CategoriaModel categoriaModel);

	Optional<CategoriaModel> registerCategoria(@Valid CategoriaModel CategoriaModel);

}
