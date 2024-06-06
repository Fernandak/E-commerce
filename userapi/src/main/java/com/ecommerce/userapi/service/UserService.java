package com.ecommerce.userapi.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.userapi.dto.UserDto;
//import com.ecommerce.userapi.model.UserCredential;
import com.ecommerce.userapi.model.UserModel;

import jakarta.validation.Valid;

public interface UserService {	
	
	Optional<UserModel> findById(Long id);

	Optional<UserDto> buscaPorCpf(String cpf);

	List<UserModel> buscaPorNome(String nome);

	Optional<UserModel> registerUser(@Valid UserModel userModel);
	
	Optional<UserModel> registerUser2(@Valid UserModel userModel);

	Optional<UserModel> updateUser(Long id, @Valid UserModel userModel);

	void deleteById(long id);

	void reativarUsuario(Long id);

}

