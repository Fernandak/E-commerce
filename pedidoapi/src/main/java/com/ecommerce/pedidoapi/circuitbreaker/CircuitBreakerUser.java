package com.ecommerce.pedidoapi.circuitbreaker;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.pedidoapi.feignclients.UserFeignClient;
import com.ecommerce.pedidoapi.model.UserModel;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CircuitBreakerUser {

	@Autowired
	UserFeignClient userFeignClient;

	// variavel p contar quantidade de tentativas
	private int tent = 1;

	public static final String USERAPI = "userapi";

	public UserModel returnUserDataFallback(Exception userExeception) { // CallNotPermittedException
		log.error("Chamada do Fallback " + userExeception);
		// return new UserModel(1L, "user", "one", "111.111.111-23", "2199999999", true,
		// null, null);
		throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
				"URL da API de usuario indisponivel no momento, tente novamente ");
	}

	@CircuitBreaker(name = USERAPI, fallbackMethod = "returnUserDataFallback")
	// @Retry(name = USERAPI, fallbackMethod = "returnUserDataFallback")
	public UserModel getUser(Long idUsuario) {
		System.out.println("Metodo de retentativa chamado " + tent++ + " vez(es) " + " as " + new Date());
		UserModel user = null;
		user = userFeignClient.getById(idUsuario).getBody();

		return user;
	}

}
