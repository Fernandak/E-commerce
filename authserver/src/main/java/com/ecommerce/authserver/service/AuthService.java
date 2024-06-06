package com.ecommerce.authserver.service;


public interface AuthService {
	
//	public String saveUser(UserCredential credential);
	
	public String generateToken(String username);
	
	public void validateToken(String token);

}
