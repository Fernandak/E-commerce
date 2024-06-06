package com.ecommerce.produtoapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.produtoapi.model.CategoriaModel;
import com.ecommerce.produtoapi.service.CategoriaService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Valid
@Validated
@RestController
@RequestMapping(value = "/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	/**
	 * busca por todos os Id's
	 */
	@GetMapping("/findall")
	public ResponseEntity<List<CategoriaModel>> findAll() {
		log.info("Chamada do findAll ");
		List<CategoriaModel> categorialist = categoriaService.findAll();

		if (categorialist.isEmpty()) {
			//regra de negocio pede para retornar array vazio
			ResponseEntity.status(HttpStatus.OK).body("[]");
		}

		return ResponseEntity.ok(categorialist);

	}

	/**
	 * busca por Id
	 */
	@GetMapping("idcategoria/{id}")
	public ResponseEntity<CategoriaModel> getById(@PathVariable Long id) {
		log.info("Chamada do getById" +id);
		if (id < 1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Desculpe, não foi possível encontrar uma categoria esse id. Verifique e tente novamente.");
		}

		return categoriaService.findByIdCategoria(id).map(res -> ResponseEntity.ok(res))
				.orElse(ResponseEntity.ok().build());
	}

	/**
	 * busca por o Id vazio
	 */
	@GetMapping(path = { "/idcategoria", "/idcategoria/" })
	public void findByIdCategoriaNull() {
		log.error("Chamada sem paramentros ");
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Desculpe, não foi possível encontrar um usuário com este id. Verifique e tente novamente.");
	}

	/**
	 * busca por nome da categoria
	 */
	@GetMapping("/nomeCategoria/{nomeCategoria}")
	public ResponseEntity<Optional<CategoriaModel>> getByNome(@PathVariable String nomeCategoria) {
		log.info("Chamada do getByNome " +nomeCategoria);

		if (nomeCategoria.length() <= 3 || nomeCategoria.length() >= 45) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"O nome não pode ter menos de 3 e mais de 45 caracteres");
		}
		Optional<CategoriaModel> categoria = categoriaService.findByNomeCategoria(nomeCategoria);

		return categoria.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(categoria);

	}

	/**
	 * busca por nome da categoria vazio
	 */

	@GetMapping(path = { "/nomeCategoria", "/nomeCategoria/" })
	public ResponseEntity<String> getByNomeNaoExiste() {
		log.error("Chamada sem paramentros ");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				"Desculpe, não foi possível realizar a busca por nome da categoria. Digite um nome e tente novamente ");
	}

	/**
	 * cadastra uma categoria
	 */

	@PostMapping("/registercategory")
	public ResponseEntity<String> registerCategoria(@Valid @RequestBody CategoriaModel categoriaModel) {
		log.info("Chamada do registerCategoria ");
		if (categoriaModel.getNomeCategoria() == null) {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					"Desculpe, não foi possível cadastrar a categoria. Preencha os campos obrigatórios e tente novamente.");

		}
		return categoriaService.registerCategoria(categoriaModel)
				.map(res -> ResponseEntity.status(HttpStatus.CREATED).body("Categoria adicionada com sucesso"))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

	}

	/**
	 * atualiza uma categoria
	 */
	@PutMapping("/update/{idCategoria}")
	public ResponseEntity<String> updateCategoria(@PathVariable Long idCategoria,
			@Valid @RequestBody CategoriaModel categoriaModel) {
		log.info("Chamada do updateCategoria ");
		return categoriaService.updateCategoria(idCategoria, categoriaModel)
				.map(res -> ResponseEntity.status(HttpStatus.OK).body("Categoria atualizada com sucesso"))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	/**
	 * atualiza uma categoria vazia
	 */
	@PutMapping(path = { "/update", "/update/" })
	public ResponseEntity<String> updateVazio() {
		log.error("Chamada sem paramentros ");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				"Desculpe, não foi possível atualizar a categoria. É necessário informar o id da categoria que deseja atualizar. Verifique e tente novamente.");
	}

	/**
	 * Exclui uma categoria passando seu id
	 */

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCategoria(@PathVariable long id) {
		log.info("Chamada do deleteCategoria ");
		if (categoriaService.deleteById(id)) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Categoria deletada com sucesso.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

	/**
	 * Exclui uma categoria sem passar seu id
	 */

	@DeleteMapping(path = { "/delete", "/delete/" })
	public ResponseEntity<String> deleteIdVazio() {
		log.error("Chamada sem paramentros ");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				"Desculpe, não foi possível deletar a categoria. É necessário informar o id da categoria que deseja deletar. Verifique e tente novamente.");
	}

}
