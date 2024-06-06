package com.ecommerce.pedidoapi.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ecommerce.pedidoapi.enums.StatusPedido;

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
import lombok.ToString;
@ToString
@Entity
@Table(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pedido", nullable = false)
	private Long idPedido;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm" , timezone="GMT-3")
	@Column(name = "data_criacao")
	private Date dataCriacao;

	@NotNull(message = "O campo  é obrigatório!")
	@Column(name = "status_pedido")
	private Integer  statusPedido;

	@Column(name = "total_pedido")
	private Double totalPedido;

	@Column(name = "id_usuario")
	private Long idUsuario;
	
	@Transient
	private List<DetalhesPedidoModel> detalhesPedidoModel;
	
	
	public StatusPedido getStatusPedido() {
		return StatusPedido.valueOf(statusPedido);
	}

	public void setOrderStatus(StatusPedido statusPedido) {
		if (statusPedido != null) {
			this.statusPedido = statusPedido.getCodigo();
		}
	}

}
