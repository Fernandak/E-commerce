package com.ecommerce.produtoapi.model;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Sub_categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoriaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_sub_categoria", nullable = false)
	private Long idSubCategoria;

	@NotBlank(message = "O nome precisa ter, pelo menos, 2 caracteres e, no máximo, 45 caracteres. Verifique e tente novamente.")
	@Size(min = 3, max = 45)
	@Length(min = 3, message = "O nome não pode ter menos de 3 e mais de 45 caracteres ")
	@Column(name = "nome_sub_categoria")
	private String nomeSubCategoria;

	@NotBlank(message = "O campo descricao é obrigatório. Verifique e tente novamente.")
	@Size(min = 15, max = 255)
	@Length(min = 15, message = "A precisa ter, pelo menos, 15 caracteres e, no máximo, 255 caracteres ")
	@Column(name = "descricao")
	private String descricao;

	@Column(name = "id_categoria")
	@NotNull(message = "O campo id Categoria é obrigatório. Verifique e tente novamente.")
	private Long idCategoria;



}
