package com.ecommerce.userapi.enums;
import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority{

	ADMIN("admin"),
	USER("user");

	Roles(String string) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getAuthority() {
		return name();
	}	

}
