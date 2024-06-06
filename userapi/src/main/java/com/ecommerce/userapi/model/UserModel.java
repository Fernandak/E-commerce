package com.ecommerce.userapi.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserModel implements UserDetails{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario", nullable = false)
	private Long idUsuario;

	@NotBlank(message = "O campo nome é obrigatório. deve ter, pelo menos, 2 caracteres.Verifique e tente novamente.")
	@Size(min = 2, max = 255)
	private String nome;

	@Size(min = 2, max = 255)
	private String nomeSocial;

	@NotBlank(message = "O campo cpf é obrigatório. Verifique e tente novamente.")
	@Size(max = 15)
	@Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "O formato do CPF não é valido exemplo:(xxx.xxx.xxx-xx)")
	private String cpf;

	@Pattern(regexp = "^\\+\\d{2,3} \\(\\d{2}\\) \\d{4,5}-\\d{4}$", message = "Telefone incorreto,formatos válidos: +xxx (xx) xxxxx-xxxx ,+xxx (xx) xxxx-xxxx ,+xx (xx) xxxxx-xxxx ,+xx (xx) xxxx-xxxx ")
	private String telefone;

	private boolean ativo = true;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "GMT-3")
	@Column(name = "dataCriacao")
	private Date dataCriacao;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "GMT-3")
	@Column(name = "dataModificacao")
	private Date dataModificacao;
	
	@NotBlank(message = "O campo nome é obrigatório. Verifique e tente novamente.")
	@Size(max = 255)
	@Email
	private String email;
	
	@NotBlank(message = "O campo nome é obrigatório.Verifique e tente novamente.")
	@Size(min = 6, max = 255)
	private String password;
	
	//@NotBlank(message = "O campo nome é obrigatório.Verifique e tente novamente.")
	private String role;
	
//	@NotBlank(message = "O campo nome é obrigatório. deve ter, pelo menos, 2 caracteres.Verifique e tente novamente.")
//	@Size(min = 2, max = 255)
//	private String name;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "GMT-3")
	@Column(name = "data_nascimento")
	private Date dataNascimento;
	
	@Transient
    private List<EnderecoModel> enderecoModel;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Configuracao do role
		return role.equals("admin") ? List.of(new SimpleGrantedAuthority("admin")) : List.of(new SimpleGrantedAuthority("user"));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
