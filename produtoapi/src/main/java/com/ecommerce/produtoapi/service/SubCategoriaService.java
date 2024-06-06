package com.ecommerce.produtoapi.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.produtoapi.model.SubCategoriaModel;

import jakarta.validation.Valid;

public interface SubCategoriaService {

	Optional<SubCategoriaModel> findById(Long id);

	boolean deleteById(long id);

	List<SubCategoriaModel> findAll();

	Optional<SubCategoriaModel> registerSubCategoria(@Valid SubCategoriaModel subCategoriaModel);

	Optional<Integer> updateSubCategoria(Long id, @Valid SubCategoriaModel subCategoriaModel);

	List<SubCategoriaModel> findByIdCategoria(Long idCategoria);


}
