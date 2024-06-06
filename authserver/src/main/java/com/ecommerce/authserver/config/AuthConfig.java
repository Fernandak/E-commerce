package com.ecommerce.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.ecommerce.authserver.model.UserModel;

import lombok.RequiredArgsConstructor;

@Configuration // presisa ser gerenciada pelo spring
@EnableWebSecurity // habilita recursos de seguranca para web
//@EnableGlobalMethodSecurity(jsr250Enabled = true)
@RequiredArgsConstructor
@EnableMethodSecurity // habilita metodos de seguranca do spring security
public class AuthConfig {

//	   @Bean
//	    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
//	        UserDetails user = UserModel.builder()
//	            .username("user")
//	            .password(passwordEncoder.encode("userpass"))
//	            .role("USER")
//	            .build();
//	        
//	        UserDetails admin = UserModel.builder()
//	            .username("admin")
//	            .password(passwordEncoder.encode("adminpass"))
//	            .role("ADMIN")
//	            .build();
//
//	        return new InMemoryUserDetailsManager(user, admin);
//	    }
	
	
	
	
	@Bean // OSS
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean // OSS configura rotas
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.csrf().disable()
//		.authorizeHttpRequests()
//		.requestMatchers("/auth/register", "/auth/token", "/user/register",
//				// ("/auth/logout"),
//				"/auth/validate")
//		.permitAll().
//		and().logout().logoutUrl("/auth/logout");
//		return http.build();
		// http.csrf(AbstractHttpConfigurer::disable)

		http.csrf(AbstractHttpConfigurer::disable)// pesquisar
				.authorizeHttpRequests(authorizeConfig -> {
					authorizeConfig.requestMatchers("/auth/token").permitAll();
					authorizeConfig.requestMatchers("/user/register/**").permitAll();
					authorizeConfig.requestMatchers("/userapi/user/**").permitAll();
					authorizeConfig.requestMatchers("/auth/logout").permitAll();
					authorizeConfig.requestMatchers("/produto/registerProduct/**").hasRole("admin");
					authorizeConfig.requestMatchers("/pedido/**").hasAnyRole("admin", "user");
					authorizeConfig.anyRequest().authenticated();

				});

		/*
		 * authorizeConfig.requestMatchers("/user/**").permitAll();
		 * authorizeConfig.requestMatchers("/produto/registerProduct").hasAnyRole(
		 * "admin");
		 * authorizeConfig.requestMatchers("/produto/delete").hasAnyRole("admin");
		 * authorizeConfig.requestMatchers("/produto/**").hasAnyRole("admin", "user");
		 * authorizeConfig.requestMatchers("/categoria/registercategory").hasAnyRole(
		 * "admin");
		 * authorizeConfig.requestMatchers("/categoria/delete").hasAnyRole("admin");
		 * authorizeConfig.requestMatchers("/categoria/**").hasAnyRole("admin", "user");
		 * authorizeConfig.requestMatchers("/subcategoria/**").hasAnyRole("admin",
		 * "user");
		 * authorizeConfig.requestMatchers("/subcategoria/registersubcategoria").
		 * hasAnyRole("admin");
		 * authorizeConfig.requestMatchers("/subcategoria/delete").hasAnyRole("admin");
		 * authorizeConfig.requestMatchers("/subcategoria/update").hasAnyRole("admin");
		 * authorizeConfig.requestMatchers("/auth/token").permitAll();
		 * authorizeConfig.requestMatchers("/auth/logout").permitAll();
		 * authorizeConfig.requestMatchers("/pedido/**").hasAnyRole("admin", "user");
		 * authorizeConfig.anyRequest().authenticated();
		 */

		return http.build();
	}

	/*
	 * @Bean // CONFIGURA AS ROTAS //aqui configura as rotas de acordo com a role
	 * public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
	 * Exception { return http.csrf(AbstractHttpConfigurer::disable)
	 * .authorizeHttpRequests(mr -> mr.requestMatchers("/auth/**").permitAll())
	 * .logout(lc -> lc.logoutUrl("/auth/logout")).build(); }
	 */
	@Bean // OSS tipo de criptografia
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean // OSS
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean // OSS valida autenticacao
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
