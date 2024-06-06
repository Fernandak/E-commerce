package com.ecommerce.pedidoapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.pedidoapi.model.PedidoModel;
import com.ecommerce.pedidoapi.service.PedidoService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Valid
@Validated
@RestController
@Controller
@RequestMapping(value = "/pedido")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	/**
	 * busca todos os pedidos
	 */
	@GetMapping("/findall/{idUsuario}")
	public ResponseEntity<List<PedidoModel>> findAll(@PathVariable Long idUsuario) {
		log.info("Chamada do findAll passando id do Usuario " + idUsuario);

		List<PedidoModel> list = pedidoService.findAllByUsuario(idUsuario);

		return list.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(list);
	}

	/**
	 * busca todos os pedido vazio
	 */
	@GetMapping(path = { "/findall", "/findall/" })
	public ResponseEntity<String> findAllByPedido() {
		log.error("Chamada sem paramentros ");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body( "Desculpe, não foi possível encontrar esse pedido. ");
	}

	/**
	 * busca pedido por Id
	 */
	//
	@SuppressWarnings("unchecked")//esconder warning unchecked
	@GetMapping("/{id}")
	public ResponseEntity<PedidoModel> getById(@PathVariable Long id) throws Exception{
	
		log.info("Chamada do getById passando id do pedido " + id);
		 Object pedido = null;
		try {
			pedido =  pedidoService.findById(id).map(res -> ResponseEntity.ok(res)).orElse(ResponseEntity.notFound().build());
			System.out.println(pedido.toString());
		} catch (ResponseStatusException e) {
			log.error(e.getMessage());
			pedido = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			pedido = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
		}
		return (ResponseEntity<PedidoModel>) pedido;
	}

	/**
	 * busca de pedido por Id quando não é passado o ID
	 */
	@GetMapping(path = { "/id", "/id/" })
	public ResponseEntity<String>  findById() {
		log.error("Chamada sem paramentros ");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Desculpe, não foi possível encontrar esse pedido. ");
	}

	/**
	 * busca por status
	 */
	@GetMapping(path = { "/status/{id}/{statusPedido}" })
	public ResponseEntity<List<PedidoModel>> getStatus(@PathVariable Long id, @PathVariable Integer statusPedido) {
		log.info("Chamada do getStatus passando id do pedido e id do status" + id, +statusPedido);
		List<PedidoModel> list = pedidoService.findAllByStatus(id, statusPedido);

		return list.isEmpty() ? ResponseEntity.notFound().build()
				: ResponseEntity.ok(pedidoService.findAllByStatus(id, statusPedido));

	}

	/**
	 * busca todos os status vazio
	 */
	@GetMapping(path = { "/status", "/status/" })
	public ResponseEntity<String> findAllByStatus() {
		log.info("Chamada do findAllByStatus sem paramentros");
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Desculpe, não foi possível encontrar pedidos com esse status. ");
	}
	

	/**
	 * Exclusão logica pedido
	 */
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<String> ExcluirPedido(@PathVariable Long id) {
		log.info("Chamada do ExcluirPedido");
		try {
			pedidoService.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("Pedido excluido com sucesso!");
		}
		catch(Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}	
	}

	/**
	 * Inserir pedido
	 * 
	 * @throws Exception
	 */
	@PostMapping("/register")
	public ResponseEntity<String> registerPedido(@Valid @RequestBody PedidoModel pedidoModel) throws Exception {
		log.info("Chamada do registerPedido passando o objeto" + pedidoModel.toString());
		ResponseEntity<String> pedido = null;
		try {
			pedido = pedidoService.registerPedido(pedidoModel)
					.map(res -> ResponseEntity.status(HttpStatus.CREATED).body("Pedido criado com sucesso"))
					.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
		} 
		catch (ResponseStatusException e) {
			log.error(e.getMessage());
			pedido = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
		}
		catch (Exception e) {
			log.error(e.getMessage());
			pedido = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		return pedido;
	}

}
