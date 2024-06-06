package com.ecommerce.pedidoapi.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;

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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_produto", nullable = false)
	private Long idProduto;

	@Column(name = "id_categoria")
	private Long IdCategoria;

	@NotBlank(message = "O campo nome do produto é obrigatório. Verifique e tente novamente.")
	@Size(min = 2, max = 45)
	@Length(min = 2, message = "Nome do produto deve ter entre 2 e 45 caracteres ")
	@Column(name = "nome_produto")
	private String nomeProduto;

	@NotNull(message = "O campo sku é obrigatório. Verifique e tente novamente.")
	@Range(min = 1, message = " O valor mínimo para esse campo é 1")
	private int sku;

	@NotBlank(message = "O campo descricao é obrigatório. Verifique e tente novamente.")
	@Size(min = 15, max = 255)
	@Length(min = 15)
	private String descricao;

	@NotNull(message = "O campo estoque é obrigatório. Verifique e tente novamente.")
	@Range(min = 1, message = " O valor não pode ser igual ou inferior a R$ 0,00")
	private int estoque;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT-3")
	@Column(name = "data_criacao")
	private Date dataCriacao;

	@NotNull(message = "O campo preco é obrigatório. Verifique e tente novamente.")
	@Range(min = 1, message = " O valor não pode ser igual ou inferior a R$ 0,00")
	@Column(name = "valor_unitario")
	private Double valorUnitario;	

}
