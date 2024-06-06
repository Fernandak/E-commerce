package com.ecommerce.userapi.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.userapi.dto.UserDto;
import com.ecommerce.userapi.model.EnderecoModel;
import com.ecommerce.userapi.model.PaisModel;
//import com.ecommerce.userapi.model.UserCredential;
import com.ecommerce.userapi.model.UserModel;
import com.ecommerce.userapi.repository.EnderecoRepository;
import com.ecommerce.userapi.repository.PaisRepository;
//import com.ecommerce.userapi.repository.UserCredentialRepository;
import com.ecommerce.userapi.repository.UserRepository;
import com.ecommerce.userapi.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PaisRepository countryRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	//private UserCredentialRepository userCredentialRepository;

//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Optional<UserModel> findById(Long id) {
		
		try {// logica para pegar o usuario da sessao
			UserModel usuario = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			var role = usuario.getAuthorities().stream().toList().get(0).getAuthority();

			// logica de seguranca de acordo com a variavel role
			// se a role for admin ele pode cadastrar admin

			if (role.equals("ADMIN")) {
				log.info("usuario ADMIN");
			} else {
				log.info("usuario USER");
			}

		} catch (Exception e) { // usario nao atenticado
			// tratamento para uduario nao autenticado
			e.printStackTrace();
			log.info("usuario nao autenticado");
		}

		Optional<UserModel> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					" Desculpe, não foi possível encontrar um usuário com este id. Verifique e tente novamente.");
		}

		return userRepository.findById(id);
	}

	@Override
	public Optional<UserDto> buscaPorCpf(String cpf) {

		Optional<UserModel> usr = userRepository.buscaPorCpf(cpf);

		if (usr.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Desculpe, nao foi possivel encontrar um usuario com este CPF. Verifique e tente novamente.");
		}

		if (!usr.get().isAtivo()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Desculpe, o usuário que você está procurando está com a conta desativada.");
		}

		UserDto dto = new UserDto(usr);

		return Optional.of(dto);
	}

	@Override
	public List<UserModel> buscaPorNome(String nome) {
		List<UserModel> buscaNome = userRepository.findAllByNomeContainingIgnoreCase(nome);
		if (buscaNome.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Não foi possível encontrar um usuário com este nome. Verifique e tente novamente.");
		}

		return buscaNome;
	}

	@Override
	public Optional<UserModel> registerUser(UserModel userModel) {

		// logica para pegar o usuario da sessao
		/*
		 * UserModel usuario = (UserModel) SecurityContextHolder.getContext()
		 * .getAuthentication().getPrincipal(); var role = *
		 * usuario.getAuthorities().stream().toList().get(0).getAuthority();
		 */

		// validacao de cpf existe
		if (userRepository.buscaPorCpf(userModel.getCpf()).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Não foi possível cadastrar o usuário. Já existe um registro cadastrado com o "
							+ "valor de um campo único. Verifique os campos CPF e Endereço e tente novamente.");
		}

		Date date = new Date();
		userModel.setDataCriacao(date);
		userModel.setDataModificacao(date);

		// verificar se o usario é admin, se sim permitir setar setAdmin(true)
		//userModel.setAdmin(false);
		

		// userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));

		UserModel model = userRepository.save(userModel);

		/*// esse trecho de codigo foi movido para classe de enderecoServiceImpl
		 * List<EnderecoModel> enderecos = userModel.getEnderecoModel(); for
		 * (EnderecoModel e : enderecos) {
		 * 
		 * // se o pais nao existir sera inserido Optional<PaisModel> pais =
		 * countryRepository.buscaPorNome(e.getPaisModel().getNome()); if
		 * (pais.isPresent()) { e.setPaisModel(pais.get()); } else { PaisModel paisModel
		 * = new PaisModel(); paisModel.setNome(e.getPaisModel().getNome());
		 * paisModel.setCodigo(e.getPaisModel().getCodigo());
		 * 
		 * countryRepository.save(paisModel); } e.setIdUsuario(model.getIdUsuario());
		 * enderecoRepository.save(e); }
		 */

		return Optional.of(model);
	}

	@Override
	public Optional<UserModel> updateUser(Long id, UserModel userModel) {

		Optional<UserModel> user = userRepository.findById(id);
		if (user.isPresent()) {

			if (!user.get().isAtivo()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Usuário com esse id esta desativado. Verifique e tente novamente.");
			}
			if (!user.get().getCpf().equals(userModel.getCpf())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Não foi possível atualizar as suas informações. Não é permitido alterar o CPF.");
			}

			Date date = new Date();
			userModel.setDataModificacao(date);

			userModel.setIdUsuario(id);

			UserModel model = userRepository.save(userModel);

			/*// esse trecho de codigo foi movido para classe de enderecoServiceImpl
			 * List<EnderecoModel> enderecos = userModel.getEnderecoModel(); for
			 * (EnderecoModel e : enderecos) {
			 * 
			 * EnderecoModel apelidoEndereco = enderecoRepository.findByApelido(id,
			 * e.getApelido()); if (apelidoEndereco != null) { throw new
			 * ResponseStatusException(HttpStatus.CONFLICT,
			 * "Não foi possível atualizar o endereco. Já existe um registro cadastrado com o valor de um campo único. Verifique o campo e Endereço e tente novamente."
			 * ); }
			 * 
			 * // se o pais nao existir sera inserido Optional<PaisModel> pais =
			 * countryRepository.buscaPorNome(e.getPaisModel().getNome()); if
			 * (pais.isPresent()) { e.setPaisModel(pais.get()); } else { PaisModel paisModel
			 * = new PaisModel(); paisModel.setNome(e.getPaisModel().getNome());
			 * paisModel.setCodigo(e.getPaisModel().getCodigo());
			 * 
			 * countryRepository.save(paisModel);
			 * 
			 * }
			 * 
			 * List<EnderecoModel> endereco = enderecoRepository.findByIdUsuario(id);
			 * e.setIdUsuario(id); if (e.isPadrao()) { for (EnderecoModel en : endereco) {
			 * en.setPadrao(false); enderecoRepository.save(en); } }
			 * 
			 * e.setIdUsuario(model.getIdUsuario()); enderecoRepository.save(e); }
			 */
			return Optional.ofNullable(model);

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Não existe nenhum usuário com esse id. Verifique e tente novamente.");
		}

	}

	@Override
	public void deleteById(long id) {

		Optional<UserModel> del = userRepository.findById(id);
		if (del.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Não existe nenhum usuário com esse id. Verifique e tente novamente.");

		}

		if (userRepository.findById(id).isPresent()) {
			UserModel userModel = userRepository.getReferenceById(id);

			if (userModel.isAtivo()) {
				userRepository.inativarUsuario(id);
			} else {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Este usuário já está com a conta desativada");
			}
		}
	}

	@Override
	public void reativarUsuario(Long id) {

		Optional<UserModel> reativar = userRepository.findById(id);
		if (reativar.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Desculpe, este usuário não existe. Verifique e tente novamente.");
		}
		if (userRepository.findById(id).isPresent()) {
			UserModel userModel = userRepository.getReferenceById(id);

			if (!userModel.isAtivo()) {
				userRepository.ativarUsuario(id);
			} else {
				throw new ResponseStatusException(HttpStatus.CONFLICT,
						"Este usuário já possui está com a conta ativa.");
			}
		}
	}

	@Override
	public Optional<UserModel> registerUser2(@Valid UserModel userModel) {

		/*
		 * try {// logica para pegar o usuario da sessao UserModel usuario = (UserModel)
		 * SecurityContextHolder.getContext().getAuthentication().getPrincipal(); var
		 * role = usuario.getAuthorities().stream().toList().get(0).getAuthority();
		 * 
		 * // logica de seguranca de acordo com a variavel role // se a role for admin
		 * ele pode cadastrar admin
		 * 
		 * if (role.equals("ADMIN")) { log.info("usuario ADMIN"); } else {
		 * log.info("usuario USER"); }
		 * 
		 * } catch (Exception e) { // usario nao atenticado // tratamento para uduario
		 * nao autenticado e.printStackTrace(); log.info("usuario nao autenticado"); }
		 */
		// validacao de cpf existe
		if (userRepository.buscaPorCpf(userModel.getCpf()).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Não foi possível cadastrar o usuário. Já existe um registro cadastrado com o "
							+ "valor de um campo único. Verifique os campos CPF e Endereço e tente novamente.");
		}

		Date date = new Date();
		userModel.setDataCriacao(date);
		userModel.setDataModificacao(date);

		// verificar se o usario é adimin, se sim permitir setar setAdmin(true)
		//userModel.setAdmin(false);
		userModel.setRole("admin");

		 userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));

		UserModel model = userRepository.save(userModel);

		/*
		 * UserCredential userCredential = new UserCredential();
		 * userCredential.setId(model.getIdUsuario().intValue());
		 * userCredential.setEmail(model.getEmail());
		 * userCredential.setName(model.getName());
		 * userCredential.setPassword(passwordEncoder.encode(userModel.getPassword()));
		 * userCredentialRepository.save(userCredential);
		 */

		userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));

		/*
		 * List<EnderecoModel> enderecos = userModel.getEnderecoModel(); for
		 * (EnderecoModel e : enderecos) {
		 * 
		 * // se o pais nao existir sera inserido Optional<PaisModel> pais =
		 * countryRepository.buscaPorNome(e.getPaisModel().getNome()); if
		 * (pais.isPresent()) { e.setPaisModel(pais.get()); } else { PaisModel paisModel
		 * = new PaisModel(); paisModel.setNome(e.getPaisModel().getNome());
		 * paisModel.setCodigo(e.getPaisModel().getCodigo());
		 * 
		 * countryRepository.save(paisModel); } e.setIdUsuario(model.getIdUsuario());
		 * enderecoRepository.save(e); }
		 */
		return Optional.of(model);
	}

}
