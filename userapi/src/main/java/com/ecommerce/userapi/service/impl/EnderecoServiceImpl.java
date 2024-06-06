package com.ecommerce.userapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.userapi.model.EnderecoModel;
import com.ecommerce.userapi.model.PaisModel;
import com.ecommerce.userapi.model.UserModel;
import com.ecommerce.userapi.repository.EnderecoRepository;
import com.ecommerce.userapi.repository.PaisRepository;
import com.ecommerce.userapi.repository.UserRepository;
import com.ecommerce.userapi.service.EnderecoService;

import jakarta.validation.Valid;

@Service
public class EnderecoServiceImpl implements EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PaisRepository countryRepository;

	@Override
	public Optional<EnderecoModel> registerEndereco(Long idUsuario, @Valid EnderecoModel enderecoModel) {

		Optional<UserModel> user = userRepository.findById(idUsuario);
		if (user.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					" Desculpe, não foi possível encontrar um usuario com este id. Verifique e tente novamente");

		}
		EnderecoModel apelido = enderecoRepository.findByApelido(user.get().getIdUsuario(), enderecoModel.getApelido());

		if (apelido != null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Não foi possível cadastrar o usuário. Já existe um registro cadastrado com o valor de um campo único. Verifique os campos CPF e Endereço e tente novamente.");
		}

		// verifica se o endereco corrente é o padrao, se for ele desabilita os outros
		// esse trecho de codigo impede que o usuario tenha mais de um endereco padrao
		EnderecoModel retorno = new EnderecoModel();
		enderecoModel.setIdUsuario(idUsuario);

		Boolean existeEndereco = false;
		List<EnderecoModel> end = findAllEnderecoByIdUsuario(user.get().getIdUsuario()).get();
		for (EnderecoModel e : end) {
			if (e.equals(enderecoModel)) {
				existeEndereco = true;
			}
		}

		if (existeEndereco) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					" Não foi possível cadastrar o endereço. Esse endereço já está cadastrado. Verifique e tente novamente.");
		}

		// se o pais nao existir sera inserido
		Optional<PaisModel> pais = countryRepository.buscaPorNome(enderecoModel.getPaisModel().getNome());
		if (pais.isPresent()) {
			enderecoModel.setPaisModel(pais.get());
		} else {
			PaisModel paisModel = new PaisModel();
			paisModel.setNome(enderecoModel.getPaisModel().getNome());
			paisModel.setCodigo(enderecoModel.getPaisModel().getCodigo());

			countryRepository.save(paisModel);
		}

		if (enderecoModel.isPadrao()) {
			for (EnderecoModel e : end) {
				e.setPadrao(false);
				enderecoRepository.save(e);
			}
			retorno = enderecoRepository.save(enderecoModel);
		} else {
			retorno = enderecoRepository.save(enderecoModel);
		}

		return Optional.of(retorno);

	}

	@Override
	public Optional<List<EnderecoModel>> findAllEnderecoByIdUsuario(Long idUsuario) {

		return enderecoRepository.findEnderecoByIdUsuario(idUsuario);
	}

	@Override
	public Optional<EnderecoModel> updateEndereco(Long idEndereco, Long idUsuario, @Valid EnderecoModel enderecoModel) {

		Optional<UserModel> user = userRepository.findById(idUsuario);
		if (user.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					" Desculpe, não foi possível encontrar um usuario com este id. Verifique e tente novamente");

		}

		Optional<EnderecoModel> busca = enderecoRepository.findEnderecoByUsuario(idEndereco, idUsuario);
		if (busca.isPresent()) {
			enderecoModel.setIdEndereco(busca.get().getIdEndereco());
		}

		Optional<PaisModel> pais = countryRepository.buscaPorNome(enderecoModel.getPaisModel().getNome());
		if (pais.isPresent()) {
			enderecoModel.setPaisModel(pais.get());
		} else {
			PaisModel paisModel = new PaisModel();
			paisModel.setNome(enderecoModel.getPaisModel().getNome());
			paisModel.setCodigo(enderecoModel.getPaisModel().getCodigo());

			countryRepository.save(paisModel);
		}

		List<EnderecoModel> endereco = enderecoRepository.findByIdUsuario(idUsuario);
		EnderecoModel retorno = new EnderecoModel();
		enderecoModel.setIdUsuario(idUsuario);

		if (enderecoModel.isPadrao()) {
			for (EnderecoModel e : endereco) {
				e.setPadrao(false);
				enderecoRepository.save(e);
			}
			retorno = enderecoRepository.save(enderecoModel);

		}
		return Optional.of(retorno);
	}

	@Override
	public void deleteById(long id) {
		EnderecoModel del = enderecoRepository.findByIdEndereco(id);
		if (del == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Não existe nenhum endereco com esse id. Verifique e tente novamente.");
		}
		boolean enderecoPadrao = del.isPadrao();

		if (enderecoPadrao) {
			List<EnderecoModel> end = findAllEnderecoByIdUsuario(del.getIdUsuario()).get();
			Optional<EnderecoModel> novoEnderecoPadrao = end.stream().findFirst();

			if (novoEnderecoPadrao.isPresent()) {
				novoEnderecoPadrao.get().setPadrao(true);
				enderecoRepository.save(novoEnderecoPadrao.get());
			}

		}

		enderecoRepository.deleteById(del.getIdEndereco());
	}

	@Override
	public Optional<EnderecoModel> findByIdEndereco(Long id) {
		EnderecoModel endereco = enderecoRepository.findByIdEndereco(id);
		if (endereco == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					" Desculpe, não foi possível encontrar um endereco com este id. Verifique e tente novamente.");
		}
		return Optional.of(endereco);
	}
}
