package com.ecommerce.authserver.service;

public interface JwtService {
	
	public void validateToken(final String token);
	
	 public String generateToken(String email); 

}
