package com.ecommerce.pedidoapi.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario", nullable = false)
	private Long idUsuario;

	@NotBlank(message = "O campo nome é obrigatório. deve ter, pelo menos, 2 caracteres.Verifique e tente novamente.")
	@Size(min = 2, max = 255)
	private String nome;

	@NotBlank(message = "O campo sobrenome é obrigatório.deve ter, pelo menos, 2 caracteres. Verifique e tente novamente.")
	@Size(min = 2, max = 255)
	private String sobrenome;

	@NotBlank(message = "O campo cpf é obrigatório. Verifique e tente novamente.")
	@Size(max = 15)
	@Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "O formato do CPF não é valido exemplo:(xxx.xxx.xxx-xx)")
	private String cpf;

	@Pattern(regexp = "^\\+\\d{2,3} \\(\\d{2}\\) \\d{4,5}-\\d{4}$", message = "Telefone incorreto,formatos válidos: +xxx (xx) xxxxx-xxxx ,+xxx (xx) xxxx-xxxx ,+xx (xx) xxxxx-xxxx ,+xx (xx) xxxx-xxxx ")
	private String telefone;

	private boolean ativo = true;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm" , timezone="GMT-3")
	@Column(name = "dataCriacao")
	private Date dataCriacao;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm" , timezone="GMT-3")
	@Column(name = "dataModificacao")
	private Date dataModificacao;

}

