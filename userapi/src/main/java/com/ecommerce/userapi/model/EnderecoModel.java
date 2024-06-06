package com.ecommerce.userapi.model;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
	private Long idEndereco;

	@NotBlank(message = "O campo rua é obrigatório. Verifique e tente novamente.")
	@Size(max = 45)
	@Column(name = "rua")
	private String rua;

	@NotNull(message = "O campo numero é obrigatório. Verifique e tente novamente.")
	@Range(max = 1000)
	@Column(name = "numero")
	private int numero;

	@NotBlank(message = "O campo cep é obrigatório. Verifique e tente novamente.")
	@Pattern(regexp = "^\\d{2}\\d{3}[-]\\d{3}$", message = "Este cep está em um formato inválido.")
	@Column(name = "cep")
	private String cep;

	@Size(max = 45)
	@Column(name = "complemento")
	private String complemento;

	@NotBlank(message = "O campo cidade é obrigatório. Verifique e tente novamente.")
	@Size(max = 45)
	@Column(name = "cidade")
	private String cidade;

	@NotBlank(message = "O campo estado é obrigatório. Verifique e tente novamente.")
	@Size(max = 45)
	@Column(name = "estado")
	private String estado;

	@NotBlank(message = "O campo bairro é obrigatório. Verifique e tente novamente.")
	@Size(max = 45)
	@Column(name = "bairro")
	private String bairro;

	@NotBlank(message = "O campo apelido é obrigatório. Verifique e tente novamente.")
	@Size(max = 45)
	@Column(name = "apelido")
	private String apelido;

	@Column(name = "padrao")
	private boolean padrao;
	
	@Size(max = 255)
	@Column(name = "referencia")
	private String referencia;

	@JoinColumn(name = "id_usuario")
	private Long idUsuario;

//	@OneToOne(cascade = CascadeType.ALL)
	@ManyToOne
	@JoinColumn(name = "id_country")
	private PaisModel paisModel;

	@Override
	public boolean equals(Object obj) {
		// se nao forem objetos da mesma classe sao objetos diferentes
		if (!(obj instanceof EnderecoModel))
			return false;

		// se forem o mesmo objeto, retorna true
		if (obj == this)
			return true;

		// aqui o cast é seguro por causa do teste feito acima
		EnderecoModel produto = (EnderecoModel) obj;

		// aqui compara atributo por atributo
		return this.idEndereco == produto.getIdEndereco() && this.rua.equals(produto.getRua())
				&& this.getNumero() == (produto.getNumero()) && this.getCep().equals(produto.getCep())
				&& this.getComplemento().equals(produto.getComplemento())
				&& this.getCidade().equals(produto.getCidade()) && this.getEstado().equals(produto.getEstado())
				&& this.getBairro().equals(produto.getBairro()) && this.getApelido().equals(produto.getApelido())
				&& this.getIdUsuario().equals(produto.getIdUsuario());

	}

}
