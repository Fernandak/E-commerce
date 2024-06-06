package com.ecommerce.authserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.authserver.dto.AuthRequest;
import com.ecommerce.authserver.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService service;

	@Autowired
	private AuthenticationManager authenticationManager;

//	@Deprecated  // Metodo mantido por questoes de estudo
//	@PostMapping("/register")
//	public String addNewUser(@RequestBody UserCredential user) {
//		return service.saveUser(user);
//		return null;
//	}

	@PostMapping("/token")
	public String getToken(@RequestBody AuthRequest authRequest) {
		String ret = "Autenticação falhou";
		try {
			Authentication authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							authRequest.getUsername(), authRequest.getPassword()));
			if (authenticate.isAuthenticated()) {
				ret = service.generateToken(authRequest.getUsername());
			} else {
				throw new RuntimeException("invalid access");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//log.error(e.getCause().toString());			
		}
		return ret;
	}

	@GetMapping("/validate")
	public String validateToken(@RequestParam("token") String token) {
		service.validateToken(token);
		return "Token is valid";
	}
}
