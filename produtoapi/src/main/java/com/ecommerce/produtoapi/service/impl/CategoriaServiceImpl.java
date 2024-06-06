package com.ecommerce.produtoapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.produtoapi.model.CategoriaModel;
import com.ecommerce.produtoapi.repository.CategoriaRepository;
import com.ecommerce.produtoapi.service.CategoriaService;

import jakarta.validation.Valid;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	public Optional<CategoriaModel> findByIdCategoria(Long id) {
		Optional<CategoriaModel> categoria = categoriaRepository.findById(id);
		if (categoria.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					" Desculpe, não foi possível encontrar uma categoria com este id. Verifique e tente novamente");
		}

		return categoriaRepository.findById(id);
	}

	@Override
	public Optional<CategoriaModel> findByNomeCategoria(String nomeCategoria) {
		return categoriaRepository.findAllByNomeCategoriaContainingIgnoreCase(nomeCategoria);
	}

	@Override
	public Optional<CategoriaModel> registerCategoria(@Valid CategoriaModel categoriaModel) {

		Optional<CategoriaModel> CatModel = categoriaRepository
				.findAllByNomeCategoriaContainingIgnoreCase(categoriaModel.getNomeCategoria());
		if (CatModel.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Essa categoria ja existe");
		}

		return Optional.of(categoriaRepository.save(categoriaModel));

	}

	@Override
	public List<CategoriaModel> findAll() {

		return categoriaRepository.findAll();
	}

	@Override
	public boolean deleteById(long id) {
		Optional<CategoriaModel> delCategoria = categoriaRepository.findById(id);
		if (delCategoria.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Desculpe, não foi possível deletar a categoria. O id informado não corresponde a nenhuma Categoria cadastrada no sistema. Verifique e tente novamente.");

		}
		Optional<CategoriaModel> categoria = categoriaRepository.findById(id);
		boolean del = true;
		if (categoria.isPresent()) {
			categoriaRepository.deleteById(id);
		} else {
			del = false;
		}

		return del;
	}

	@Override
	public Optional<Integer> updateCategoria(Long id, CategoriaModel categoriaModel) {

		Optional<CategoriaModel> categoria = categoriaRepository.findById(id);
		if (categoria.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					" Desculpe, não foi possível atualizar a categoria. O id informado não corresponde a nenhuma Categoria cadastrada no sistema. Verifique e tente novamente.");

		}

		if (categoriaRepository.findById(id).isPresent()) {

			return Optional.ofNullable(categoriaRepository.updateCategoria(id, categoriaModel.getNomeCategoria()));

		}

		return Optional.empty();
	}

}
