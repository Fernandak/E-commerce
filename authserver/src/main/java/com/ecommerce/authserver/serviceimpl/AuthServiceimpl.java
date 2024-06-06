package com.ecommerce.authserver.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.authserver.service.AuthService;

@Service
public class AuthServiceimpl implements AuthService{

//    @Autowired
//    private UserCredentialRepository repository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtServiceImpl jwtService;

//    public String saveUser(UserCredential credential) {
//        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
//        repository.save(credential);
//        return "user added to the system";
//    }

    public String generateToken(String email) {
        return jwtService.generateToken(email);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }



}
