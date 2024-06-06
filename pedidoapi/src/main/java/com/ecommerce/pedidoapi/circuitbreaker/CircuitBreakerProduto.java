package com.ecommerce.pedidoapi.circuitbreaker;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.pedidoapi.feignclients.ProdutoFeignClient;
import com.ecommerce.pedidoapi.model.ProdutoModel;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CircuitBreakerProduto {

	@Autowired
	ProdutoFeignClient produtoFeignClient;

	private int tent = 1;

	public static final String PRODUTOAPI = "produtoapi";

	public ProdutoModel returnProdutoDataFallback(Exception produtoExeception) throws Exception {
		log.error("Chamada do Fallback " + produtoExeception);
//		return new ProdutoModel(1L, 1L, "Celular", 01, "Celular Samsung", 10, new Date(), 100.0);
		throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
				"URL da API de produto indisponivel no momento, tente novamente ");
	}

	@CircuitBreaker(name = PRODUTOAPI, fallbackMethod = "returnProdutoDataFallback")
	// @Retry(name = PRODUTOAPI, fallbackMethod = "returnProdutoDataFallback")
	public ProdutoModel getProduto(Long idProduto) {
		System.out.println("Metodo de retentativa chamado " + tent++ + " vezes " + " as " + new Date());
		ProdutoModel produto = null;
		produto = produtoFeignClient.getById(idProduto).getBody();

		return produto;
	}

}
