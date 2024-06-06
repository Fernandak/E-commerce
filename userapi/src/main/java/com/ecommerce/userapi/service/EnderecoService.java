package com.ecommerce.userapi.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.userapi.model.EnderecoModel;
import com.ecommerce.userapi.model.UserModel;

import jakarta.validation.Valid;

public interface EnderecoService {
	
	Optional<EnderecoModel> registerEndereco(Long idUsuario, @Valid EnderecoModel enderecoModel);

	Optional<EnderecoModel> updateEndereco( Long idEndereco,Long idUsuario, @Valid EnderecoModel enderecoModel);

	void deleteById(long id);

	Optional<List<EnderecoModel>> findAllEnderecoByIdUsuario(Long idUsuario);
	
	Optional<EnderecoModel> findByIdEndereco(Long id);
}
