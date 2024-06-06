package com.ecommerce.userapi.controller;

import java.util.List;

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

import com.ecommerce.userapi.model.EnderecoModel;
import com.ecommerce.userapi.model.UserModel;
import com.ecommerce.userapi.repository.PaisRepository;
import com.ecommerce.userapi.service.EnderecoService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Valid
@Validated
@RestController
@RequestMapping(value = "/endereco")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;

	/**
	 * registra endereco
	 */
	@PutMapping("/register/{idUsuario}")
	public ResponseEntity<String> register(@PathVariable Long idUsuario,
			@Valid @RequestBody EnderecoModel enderecoModel) {
		log.info("Chamada do register passando " + enderecoModel.toString());
		return enderecoService.registerEndereco(idUsuario, enderecoModel)
				.map(res -> ResponseEntity.status(HttpStatus.CREATED).body("Endereco adicionado com sucesso"))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	/**
	 * atualiza endereco
	 */
	@PutMapping("/update/{idEndereco}/{idUsuario}")
	public ResponseEntity<String> updateEndereco(@PathVariable Long idEndereco, @PathVariable Long idUsuario,
			@Valid @RequestBody EnderecoModel enderecoModel) {
		log.info("Chamada do updateEndereco passando" + idUsuario + idEndereco + " " + enderecoModel.toString());
		return enderecoService.updateEndereco(idEndereco, idUsuario, enderecoModel)
				.map(res -> ResponseEntity.status(HttpStatus.OK).body("Endereco atualizado com sucesso!"))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	/**
	 * busca de endereco por Id
	 */
	@GetMapping("/{id}")
	public ResponseEntity<EnderecoModel> getById(@PathVariable Long id) {
		log.info("Chamada do getById passando " + id);
		return enderecoService.findByIdEndereco(id).map(res -> ResponseEntity.ok(res))
				.orElse(ResponseEntity.notFound().build());
	}

	/**
	 * busca de endereco por IdUsuario
	 */
	@GetMapping("/usuario/{idUsuario}")
	public ResponseEntity<List<EnderecoModel>> getEnderecoByIdusuario(@PathVariable Long idUsuario) {
		log.info("Chamada do getById passando " + idUsuario);

		return enderecoService.findAllEnderecoByIdUsuario(idUsuario).map(res -> ResponseEntity.ok(res))
				.orElse(ResponseEntity.ok().build());
	}

	/**
	 * Deletar endereco
	 */
	@DeleteMapping("/excluir/{idEndereco}")
	public ResponseEntity<String> DeletarEndereco(@PathVariable Long idEndereco) {
		log.info("Chamada do DeletarEndereco ");
 
		enderecoService.deleteById(idEndereco);

		return ResponseEntity.status(HttpStatus.OK).body("Endereco deletado com sucesso!");

	}

}