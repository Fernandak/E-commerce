package com.ecommerce.pedidoapi.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detalhes_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalhesPedidoModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_detalhes_pedido", nullable = false)
	private Long idDetalhesPedido;

	@Column(name = "id_pedido", nullable = false)
	private Long idPedido;

	@NotNull(message = "O campo  é obrigatório!")
	@Column(name = "id_produto")
	private Long idProduto;

	@NotNull(message = "O campo  é obrigatório!")
	@Column(name = "quantidade")
	private Long quantidade;

	
	@Column(name = "sub_total")
	private Double subTotal;	
	
	@Transient
	private ProdutoModel produtoModel;

}
