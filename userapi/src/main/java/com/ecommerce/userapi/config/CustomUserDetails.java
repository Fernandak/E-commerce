
package com.ecommerce.userapi.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.userapi.model.UserModel;

//import com.ecommerce.userapi.model.UserCredential;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String email;
	private String password;

	public CustomUserDetails(UserModel user) {
		this.email = user.getEmail();
		this.password = user.getPassword();
	}

	 @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	    	//Configuracao do role
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
