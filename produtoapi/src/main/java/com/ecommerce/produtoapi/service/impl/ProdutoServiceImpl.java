package com.ecommerce.produtoapi.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.produtoapi.model.CategoriaModel;
import com.ecommerce.produtoapi.model.ProdutoModel;
import com.ecommerce.produtoapi.repository.CategoriaRepository;
import com.ecommerce.produtoapi.repository.ProdutoRepository;
import com.ecommerce.produtoapi.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	public Optional<ProdutoModel> findById(Long id) {
		Optional<ProdutoModel> produto = produtoRepository.findById(id);
		if (produto.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					" Desculpe, não foi" + "possível encontrar um produto com este id. Verifique e tente novamente");
		}

		return produtoRepository.findById(id);
	}

	@Override
	public List<ProdutoModel> findByNomeProduto(String nomeProduto) {
		return produtoRepository.findAllByNomeProdutoContainingIgnoreCase(nomeProduto);
	}

	@Override
	public Optional<ProdutoModel> registerProduct(ProdutoModel produtoModel) {
		

		List<ProdutoModel> produto = produtoRepository.findBySKU(produtoModel.getSku());

		if (!produto.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto com esse SKU já existe");
		}

		if (!produtoRepository.findAllByNomeProdutoContainingIgnoreCase(produtoModel.getNomeProduto()).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Esse produto ja existe");
		}
		
		long idCategoria = (long) produtoModel.getIdCategoria();
		Optional<CategoriaModel> categoria = categoriaRepository.findById(idCategoria);	
		
		if (categoria.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Essa categoria não existe");
		}
		
		produtoModel.setIdCategoria(categoria.get().getIdCategoria());
		
		Date date = new Date();
		produtoModel.setDataCriacao(date);

		return Optional.of(produtoRepository.save(produtoModel));
	}

	@Override
	public List<ProdutoModel> findAllByNomeProdutoContainingIgnoreCase(String nomeProduto) {
		return null;
	}

	@Override
	public int getQuantidadeEstoque(Long idProduto) {
		return produtoRepository.getQuantidadeEstoque(idProduto);
	}

	@Override
	public List<ProdutoModel> findProdutoByCategoria(Long idCategoria) {
		return produtoRepository.findProdutoByCategoria(idCategoria);
	}
	
	@Override
	public int setBaixaEstoque(Long idProduto, int quantidade) {
		return produtoRepository.setBaixaEstoque(idProduto, quantidade);
	}

}
