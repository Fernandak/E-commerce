package com.ecommerce.userapi.dto;

import java.util.Date;
import java.util.Optional;

import com.ecommerce.userapi.model.UserModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Long idUsuario;

	private String nome;

	private String nomeSocial;

	private String cpf;

	private String telefone;

	private Date dataCriacao;

	private Date dataModificacao;
	
	private Date dataNascimento;

	public UserDto(UserModel usuarioAtivo) {
	}

	public UserDto(Optional<UserModel> usr) {
		super();
		this.idUsuario = usr.get().getIdUsuario();
		this.nome = usr.get().getNome();
		this.nomeSocial = usr.get().getNomeSocial();
		this.cpf = usr.get().getCpf();
		this.telefone = usr.get().getTelefone();
		this.dataCriacao = usr.get().getDataCriacao();
		this.dataModificacao = usr.get().getDataModificacao();
		this.dataNascimento = usr.get().getDataNascimento();
	}

}
