package com.ecommerce.produtoapi.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario", nullable = false)
	private Long idUsuario;

	@NotBlank(message = "O campo nome é obrigatório. Verifique e tente novamente.")
	@Length(min = 2, message = "O campo nome é obrigatório.deve ter, pelo menos, 2 caracteres.Verifique e tente novamente. ")
	private String nome;

	@NotBlank(message = "O campo sobrenome é obrigatório. Verifique e tente novamente.")
	@Size(min = 2, max = 255)
	private String sobrenome;

	@NotBlank(message = "O campo cpf é obrigatório. Verifique e tente novamente.")
	@Size(max = 15)
	@Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "O formato do CPF não é valido exemplo:(xxx.xxx.xxx-xx)")
	@CPF
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

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_endereco")
	@JsonIgnoreProperties("user")
	private EnderecoModel enderecoModel;

}
