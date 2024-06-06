package com.ecommerce.pedidoapi.enums;

public enum StatusPedido {

	AGPAGAMENTO(1), // Aguardando Pagamento
	EFETUADO(2), PROCESSADO(3), CONCLUIDO(4), CANCELADO(5);

	private int codigo;

	private StatusPedido(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigo() {
		return codigo;
	}

	public static StatusPedido valueOf(int codigo) {
		for (StatusPedido value : StatusPedido.values()) {
			if (value.getCodigo() == codigo) {
				return value;
			}
		}
		throw new IllegalArgumentException("Status inavalido");
	}

}