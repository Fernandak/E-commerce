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
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.userapi.dto.UserDto;
//import com.ecommerce.userapi.model.UserCredential;
import com.ecommerce.userapi.model.UserModel;
import com.ecommerce.userapi.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
//import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Valid
@Validated
@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	/**
	 * cadastrar usuário
	 */
//	@PostMapping("/register")
//	public ResponseEntity<String> register(@Valid @RequestBody UserModel userModel) {
//		log.info("Chamada do register passando " + userModel.toString());
//
//		return userService.registerUser(userModel)
//				.map(res -> ResponseEntity.status(HttpStatus.CREATED).body("Usuario adicionado com sucesso"))
//				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
//	}
	
	@PostMapping("/register")
	//@Deprecated
	public ResponseEntity<String> register2(@Valid @RequestBody UserModel userModel) {
		log.info("Chamada do register passando " + userModel.toString());
		
		return userService.registerUser2(userModel)
				.map(res -> ResponseEntity.status(HttpStatus.CREATED).body("Usuario adicionado com sucesso"))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	/**
	 * busca de usuário por Id
	 */
	@GetMapping("/{id}")
	public ResponseEntity<UserModel> getById(@PathVariable Long id) {
		log.info("Chamada do getById passando " + id);

		return userService.findById(id).map(res -> ResponseEntity.ok(res)).orElse(ResponseEntity.notFound().build());
	}

	/**
	 * busca de usuário por Id quando não é passado o ID
	 */
	@GetMapping(path = { "/id", "/id/" })
	public ResponseEntity<String> findById() {
		log.error("Chamada sem paramentros");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Desculpe, não foi possível encontrar um usuário com este id. Verifique e tente novamente. ");

	}

	/**
	 * busca de usuário por CPF TODO formatar msg erro formatacao cpf e cpf nao
	 * existe
	 */
	@SuppressWarnings("unchecked") // hide unchecked warning
	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<UserDto> getByCpf(
			@PathVariable @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", 
			message = "Desculpe, não foi possível realizar a busca por CPF. O CPF não foi digitado corretamente. Verifique e tente novamente.") String cpf)
			throws Exception {
		
		log.info("Chamada do getByCpf passando " + cpf);
		Object busca = null;
		try {
			busca = userService.buscaPorCpf(cpf).map(res -> ResponseEntity.ok(res))
					.orElse(ResponseEntity.notFound().build());
		} catch (ResponseStatusException e) {
			log.error(e.getMessage());
			busca = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			busca = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

		return (ResponseEntity<UserDto>) busca;
	}

	/**
	 * busca de usuário por CPF quando não é passado o CPF
	 */
	@GetMapping(path = { "/cpf", "/cpf/" })
	public ResponseEntity<String> buscacpf() {
		log.error("Chamada sem paramentros");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Desculpe, não foi possível realizar a busca por CPF. Digite um CPF e tente novamente. ");

	}

	/**
	 * busca de usuário por nome
	 */
	// TODO msg de erro nao esta formatada //noe com menos 2 caracter

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<UserModel>> getByNome(
			@PathVariable @Valid @Size(min = 2, max = 255, message = "Desculpe, não foi possível realizar a busca por nome. O nome informado deve ter, pelo menos, 2 caracteres. ") String nome)
			throws Exception {
		log.info("Chamada do getByNome passando " + nome);

		List<UserModel> list = null;
		try {
			list = userService.buscaPorNome(nome);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return list == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(userService.buscaPorNome(nome));

	}

	/**
	 * busca de usuário por nome quando não é passado o nome
	 */
	@GetMapping(path = { "/nome", "/nome/" })
	public ResponseEntity<String> getByNomeNaoExiste() {
		log.error("Chamada sem paramentros ");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Desculpe, não foi possível realizar a busca por nome. Digite um nome e tente novamente");
	}

	/**
	 * cadastrar usuário duplcado??
	 */
	/*
	 * @PostMapping("/register") public ResponseEntity<String>
	 * register(@Valid @RequestBody UserModel userModel) {
	 * log.info("Chamada do register passando " + userModel.toString()); return
	 * userService.registerUser(userModel) .map(res ->
	 * ResponseEntity.status(HttpStatus.CREATED).
	 * body("Usuario adicionado com sucesso"))
	 * .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()); }
	 */

	/**
	 * atualiza usuário por Id
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateUser(@PathVariable Long id, @Valid @RequestBody UserModel userModel) {
		log.info("Chamada do updateUser passando" + id + " " + userModel.toString());
		return userService.updateUser(id, userModel)
				.map(res -> ResponseEntity.status(HttpStatus.OK).body("Usuário atualizado com sucesso!"))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	/**
	 * atualiza usuário por Id quando não é passado id
	 */
	@PutMapping(path = { "/update", "/" })
	public ResponseEntity<String> updateUser() {
		log.error("Chamada sem paramentros ");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Não existe nenhum usuário com esse id. Verifique e tente novamente.");
	}

	/**
	 * deleta usuário tratamento sem id
	 */
	@DeleteMapping("/")
	public ResponseEntity<String> deleteIdVazio() {
		log.error("Chamada sem paramentros ");
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("Não existe nenhum usuário com esse id. Verifique e tente novamente.");
	}

	/**
	 * ativa usuário por id
	 */
	@PutMapping("/reativar/{id}")
	public ResponseEntity<String> reativar(@PathVariable Long id) {
		log.info("Chamada do reativar passando " + id);
		try {
			userService.reativarUsuario(id);
			return ResponseEntity.status(HttpStatus.OK).body("Conta foi reativada com sucesso!");
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Exclusão logica usuario
	 */
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<String>  deletearUsuario(@PathVariable Long id) {
		log.info("Chamada do DeletearUsuario " + id);	
		try {
			userService.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("Conta desativada com sucesso!");
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	/**
	 * Ativa usuário tratamento sem id
	 */
	@PutMapping(path = { "/reativar/", "/reativar" })
	public ResponseEntity<String> reativarVazio() {
		log.error("Chamada sem paramentros ");
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("Não existe nenhum usuário com esse id. Verifique e tente novamente.");
	}
}
