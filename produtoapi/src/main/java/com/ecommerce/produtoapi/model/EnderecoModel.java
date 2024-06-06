package com.ecommerce.produtoapi.model;

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

@Entity
@Table(name = "endereco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_endereco", nullable = false)
	private int id_endereco;

	@NotBlank(message = "O campo rua é obrigatório. Verifique e tente novamente.")
	@Size(max = 45)
	private String rua;

	@NotBlank(message = "O campo numero é obrigatório. Verifique e tente novamente.")
	@Size(max = 10)
	private int numero;

	@NotBlank(message = "O campo cep é obrigatório. Verifique e tente novamente.")
	@Pattern(regexp = "^\\d{2}\\d{3}[-]\\d{3}$", message = "Este cep está em formato inválido.")
	private String cep;

	@Size(max = 45)
	private String complemento;

	@NotBlank(message = "O campo cidade é obrigatório. Verifique e tente novamente.")
	@Size(max = 45)
	private String cidade;

	@NotBlank(message = "O campo estado é obrigatório. Verifique e tente novamente.")
	@Size(max = 45)
	private String estado;

	@NotBlank(message = "O campo bairro é obrigatório. Verifique e tente novamente.")
	@Size(max = 45)
	private String bairro;

}
