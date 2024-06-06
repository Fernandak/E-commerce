package com.ecommerce.produtoapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.jwt.Jwt;

import com.ecommerce.produtoapi.model.SubCategoriaModel;
import com.ecommerce.produtoapi.service.SubCategoriaService;

//import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/subcategoria")
public class SubCategoriaController {

	@Autowired
	private SubCategoriaService subCategoriaServise;

	/**
	 * busca por todas as subcategorias
	 */
	@GetMapping("/findall")
	public ResponseEntity<List<SubCategoriaModel>> findAll() {
		log.info("Chamada do findAll");
		List<SubCategoriaModel> subcategoria = subCategoriaServise.findAll();

		if (subcategoria.isEmpty()) {
			ResponseEntity.status(HttpStatus.OK).body("[]");
		}

		return ResponseEntity.ok(subcategoria);

	}

	/**
	 * busca por id subcategorias
	 */
	@GetMapping("id/{id}")
	public ResponseEntity<SubCategoriaModel> getById(@PathVariable Long id) {
		log.info("Chamada do getById");
		if (id < 1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Ops! id inválido, digite um número que não seja negativo.");
		}
		return subCategoriaServise.findById(id).map(res -> ResponseEntity.ok(res))
				.orElse(ResponseEntity.notFound().build());
	}

	/**
	 * busca por id vazio
	 */
	@GetMapping(path = { "/id", "/id/" })
	public ResponseEntity<String> findByIdNull() {
		log.error("Chamada sem paramentros ");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				"Desculpe, não foi possível realizar a busca. É necessário informar o id de uma categoria. Verifique e tente novamente");
	}

	/**
	 * cadastro subcategoria
	 */
	@PostMapping("/registersubcategoria")//@AuthenticationPrincipal Jwt jwt,
//	@RolesAllowed("ADMIN")
	public ResponseEntity<String> registerSubCategoria(@Valid @RequestBody SubCategoriaModel subCategoriaModel)
			throws Exception {
		//System.out.println(jwt.getTokenValue());
		
		log.info("Chamada do registerSubCategoria");
		if (subCategoriaModel.getDescricao() == null || subCategoriaModel.getNomeSubCategoria() == null) {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					"Desculpe, não foi possível cadastrar a subcategoria. Preencha os campos obrigatórios e tente novamente.");

		}

		ResponseEntity<String> subCategoria = null;

		try {
			subCategoria = subCategoriaServise.registerSubCategoria(subCategoriaModel).map(
					res -> ResponseEntity.status(HttpStatus.CREATED).body("A subcategoria foi cadastrada com sucesso!"))
					.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
		} catch (ResponseStatusException e) {
			log.error(e.getMessage());
			subCategoria = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			subCategoria = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return subCategoria;
	}

	/**
	 * atualizar subcategoria
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateSubCategoria(@PathVariable Long id,
			@Valid @RequestBody SubCategoriaModel subCategoriaModel) {
		log.info("Chamada do updateSubCategoria");
		return subCategoriaServise.updateSubCategoria(id, subCategoriaModel)
				.map(res -> ResponseEntity.status(HttpStatus.OK).body("SubCategoria atualizada com sucesso"))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	/**
	 * atualizar subcategoria vazio
	 */
	@PutMapping(path = { "/update", "/update/" })
	public ResponseEntity<String> updateVazio() {
		log.error("Chamada sem paramentros ");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Não é possível atualizar uma subcategoria sem especificar um id. Verifique e tente novamente. ");
	}

	/**
	 * Excluir subcategoria
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteSubCategoria(@PathVariable long id) {
		log.info("Chamada do deleteSubCategoria");
		boolean deletou = subCategoriaServise.deleteById(id);

		if (deletou) {
			return ResponseEntity.status(HttpStatus.OK).body("Sub Categoria deletada com sucesso.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

	/**
	 * excluir subcategoria vazio
	 */
	@DeleteMapping(path = { "/delete", "/delete/" })
	public ResponseEntity<String> deleteIdVazio() {
		log.error("Chamada sem paramentros ");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				"Desculpe, não foi possível deletar a subcategoria. É necessário informar o id da subcategoria que deseja deletar. Verifique e tente novamente. ");
	}

	@GetMapping("/buscacategoria/{idCategoria}")
	public ResponseEntity<List<SubCategoriaModel>> getByCategoria(@PathVariable Long idCategoria) {
		log.info("Chamada do getByCategoria");
		List<SubCategoriaModel> subCategoria = subCategoriaServise.findByIdCategoria(idCategoria);

		return subCategoria.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(subCategoria);

	}

}
