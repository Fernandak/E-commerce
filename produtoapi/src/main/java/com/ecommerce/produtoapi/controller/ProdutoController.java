package com.ecommerce.produtoapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.produtoapi.model.ProdutoModel;
import com.ecommerce.produtoapi.service.ProdutoService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/produto")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	/**
	 * busca por Id
	 */
	@GetMapping("id/{id}")
	public ResponseEntity<ProdutoModel> getById(@PathVariable Long id) {
		Optional<ProdutoModel> produto = produtoService.findById(id);
		log.info("Chamada do getById "+id);

		if (produto.isPresent()) {
			return ResponseEntity.ok(produto.get());
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID Ivalido");

	}

	/**
	 * busca por Id vazio
	 */
	@GetMapping(path = { "/id", "/id/" })
	public ResponseEntity<String> findByIdNull() {
		log.error("Chamada sem paramentros ");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Desculpe, não foi possível encontrar um produto com este id. Verifique e tente novamente");
	}

	/**
	 * busca por produto
	 */
	@GetMapping("/nomeProduto/{nomeProduto}")
	public ResponseEntity<List<ProdutoModel>> getByNomeProduto(@PathVariable String nomeProduto) {
		log.info("Chamada do getByNomeProduto "+nomeProduto);
		if (nomeProduto.length() <= 2 || nomeProduto.length() >= 45) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Nome do produto deve ter entre 2 e 45 caracteres");
		}
		List<ProdutoModel> list = produtoService.findByNomeProduto(nomeProduto);
		return list.isEmpty() ? ResponseEntity.notFound().build()
				: ResponseEntity.ok(produtoService.findByNomeProduto(nomeProduto));

	}

	/**
	 * busca produto vazio
	 */
	@GetMapping(path = { "/nomeProduto", "/nomeProduto/" })
	public ResponseEntity<String> findByNomeNull() {
		log.error("Chamada sem paramentros ");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Desculpe,não foi possível encontrar um produto com este nome. Verifique e tente novamente");
	}

	/**
	 * cadastra produto
	 */
	@PostMapping("/registerProduct")
	@RolesAllowed("admin")
	public ResponseEntity<String> registerProduct(@Valid @RequestBody ProdutoModel produtoModel) throws Exception {
		log.info("Chamada do registerProduct ");
		ResponseEntity<String> produto = null;
		try {
			produto = produtoService.registerProduct(produtoModel)
					.map(res -> ResponseEntity.status(HttpStatus.CREATED).body("Produto adicionado com sucesso"))
					.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
		} catch (Exception e) {
			log.error(e.getMessage());
			produto = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return produto;
	}

	/**
	 * busca por sku
	 */
	@GetMapping("sku/{sku}")
	public ResponseEntity<ProdutoModel> getBySku(@PathVariable Long id) {
		log.info("Chamada do getBySku ");
		return produtoService.findById(id).map(res -> ResponseEntity.ok(res)).orElse(ResponseEntity.notFound().build());
	}

	/**
	 * busca por sku invalido
	 */
	@GetMapping(path = { "/sku", "/sku/" })
	public ResponseEntity<String> findBySkuNull() {
		log.info("Chamada do findBySkuNull ");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("sku  Invalido");
	}

	/**
	 * busca por categoria que retorna seus produtos
	 */
	@GetMapping("/buscacategoria/{idCategoria}")
	public ResponseEntity<List<ProdutoModel>> getByCategoria(@PathVariable Long idCategoria) {
		log.info("Chamada do getByCategoria ");
		List<ProdutoModel> produto = produtoService.findProdutoByCategoria(idCategoria);

		return produto.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(produto);

	}

	/**
	 * chamada baixa estoque
	 */
	@PostMapping("baixaEstoque/{id}/{quantidade}")
	public ResponseEntity<ProdutoModel> setBaixaEstoque(@PathVariable Long id, @PathVariable int quantidade) {
		log.info("Chamada do setBaixaEstoque ");
		if (produtoService.setBaixaEstoque(id, quantidade) > 0)
			return ResponseEntity.ok().build();
		else
			return ResponseEntity.notFound().build();
	}

}
