package com.ecommerce.produtoapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.produtoapi.model.CategoriaModel;
import com.ecommerce.produtoapi.model.SubCategoriaModel;
import com.ecommerce.produtoapi.repository.CategoriaRepository;
import com.ecommerce.produtoapi.repository.SubCategoriaRepository;
import com.ecommerce.produtoapi.service.SubCategoriaService;

import jakarta.validation.Valid;

@Service
public class SubCategoriaServiceImpl implements SubCategoriaService {

	@Autowired
	private SubCategoriaRepository subCategoriaRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	public List<SubCategoriaModel> findAll() {
		return subCategoriaRepository.findAll();
	}

	public Optional<SubCategoriaModel> findById(Long id) {
		Optional<SubCategoriaModel> subCategoria = subCategoriaRepository.findById(id);
		if (subCategoria.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Desculpe, a subcategoria informada não existe. Informe o id de uma subcategoria já cadastrada no sistema e tente novamente");
		}

		return subCategoriaRepository.findById(id);
	}

	@Override
	public Optional<SubCategoriaModel> registerSubCategoria(@Valid SubCategoriaModel subCategoriaModel) {

		Optional<CategoriaModel> categoria = categoriaRepository.findById(subCategoriaModel.getIdCategoria());
		if (categoria.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					" Desculpe, não foi possível cadastrar a sub_categoria. É obrigatório inserir o id de uma categoria existente no sistema. Verifique e tente novamente.");

		}

		Optional<SubCategoriaModel> subCatModel = subCategoriaRepository
				.findAllByNomeSubCategoriaContainingIgnoreCase(subCategoriaModel.getNomeSubCategoria());
		if (subCatModel.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Essa  subcategoria ja existe");
		}

		return Optional.of(subCategoriaRepository.save(subCategoriaModel));

	}

	@Override
	public Optional<Integer> updateSubCategoria(Long id, SubCategoriaModel subCategoriaModel) {

		Optional<SubCategoriaModel> subcategoria = subCategoriaRepository.findById(subCategoriaModel.getIdCategoria());
		if (subcategoria.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					" Desculpe, não foi possível cadastrar a subcategoria. É obrigatório inserir o id de uma subcategoria existente no sistema. Verifique e tente novamente.");

		}

		if (subCategoriaRepository.findById(id).isPresent()) {

			return Optional.ofNullable(subCategoriaRepository.updateSubCategoria(id, subCategoriaModel));
		}

		return Optional.empty();
	}

	@Override
	public boolean deleteById(long id) {
		Optional<SubCategoriaModel> delSubCategoria = subCategoriaRepository.findById(id);
		if (delSubCategoria.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Desculpe, não foi possível deletar a subcategoria. O id informado não corresponde a nenhuma subcategoria cadastrada no sistema. Verifique e tente novamente.");
		}
		Optional<SubCategoriaModel> subCategoria = subCategoriaRepository.findById(id);

		boolean del = true;// variavel para controle da logica de delecao
		if (subCategoria.isPresent()) {
			subCategoriaRepository.deleteById(id);
		} else {
			del = false;
		}

		return del;
	}

	@Override
	public List<SubCategoriaModel> findByIdCategoria(Long idCategoria) {
		return subCategoriaRepository.findByIdCategoria(idCategoria);
	}

}
