package com.ecommerce.userapi.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "country")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL) //As alterações de cache são feitas em transações XA distribuídas
public class PaisModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_country")
	private int idCountry;

	@NotBlank(message = "O campo nome é obrigatório. Verifique e tente novamente.")
	@Size(max = 45)
	@Column(name = "nome")
	private String nome;

	@NotBlank(message = "O campo codigo é obrigatório. Verifique e tente novamente.")
	@Size(max = 45)
	@Pattern(regexp = "^\\+\\d{2,3}$", message = "O formato do código está inválido. (+xxx ou +xx)")
	@Column(name = "codigo")
	private String codigo;

}
