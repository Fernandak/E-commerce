package com.ecommerce.authserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.authserver.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{
	
	Optional<UserModel> findByEmail(String email);

}
