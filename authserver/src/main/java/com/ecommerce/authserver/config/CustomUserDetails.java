
package com.ecommerce.authserver.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.authserver.model.UserModel;

//import com.ecommerce.authserver.model.UserCredential;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//TODO verificar necessidade dessa classe
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails { // OSS classe usada para validar as cresencias

	private static final long serialVersionUID = 1L;

	private String email;
	private String password;

//	public CustomUserDetails(UserCredential userCredential) {
//		System.out.println(" <<<<<<passou aqui>>>>>>");
//		this.email = userCredential.getEmail();
//		this.password = userCredential.getPassword();
//	}
	
	public CustomUserDetails(UserModel userModel) {
		this.email = userModel.getEmail();
		this.password = userModel.getPassword();
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Configuracao do role
		return List.of(new SimpleGrantedAuthority("USER"));
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
