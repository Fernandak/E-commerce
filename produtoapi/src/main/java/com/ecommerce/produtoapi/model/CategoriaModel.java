package com.ecommerce.produtoapi.model;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_categoria", nullable = false)
	private Long idCategoria;

	@NotBlank(message = "O nome precisa ter, pelo menos, 3 caracteres e, no máximo, 45 caracteres. Verifique e tente novamente.")
	@Size(min = 3, max = 45)
	@Length(min = 3)
	@Column(name = "nome_categoria")
	private String nomeCategoria;

}
