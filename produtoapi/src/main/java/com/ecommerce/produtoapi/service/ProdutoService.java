package com.ecommerce.produtoapi.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.produtoapi.model.ProdutoModel;

import jakarta.validation.Valid;

public interface ProdutoService {

	Optional<ProdutoModel> findById(Long id);

	List<ProdutoModel> findAllByNomeProdutoContainingIgnoreCase(String nomeProduto);

	Optional<ProdutoModel> registerProduct(@Valid ProdutoModel produtoModel);

	List<ProdutoModel> findByNomeProduto(String produto);

	// int getQuantidadeProduto(Long idProduto);

	 int setBaixaEstoque(Long idProduto, int quantidade);

	List<ProdutoModel> findProdutoByCategoria(Long idCategoria);

	int getQuantidadeEstoque(Long idProduto);

}
