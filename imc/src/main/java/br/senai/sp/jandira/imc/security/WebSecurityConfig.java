package br.senai.sp.jandira.imc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;
	
	// *** Autenticação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
//		auth.inMemoryAuthentication()
//			
//			.withUser("celso")
//				.password(passwordEncoder().encode("123"))
//				.roles("ADMIN")
//			
//			.and()
//			.withUser("ana")
//				.password(passwordEncoder().encode("123"))
//				.roles("ADMIN");
		System.out.println("--------- autenticando ...");
		auth.userDetailsService(userDetailsService);
					
	}
	
	// *** Autorização
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		http.httpBasic()
//			.and()
//			.authorizeRequests()
//				.antMatchers("/imc/biometrias/**").permitAll()
//				.anyRequest().authenticated()
//			
//			.and()
//				.sessionManagement()
//					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//					
//			.and()
//				.csrf().disable();
		
//		http.authorizeRequests()
//			.antMatchers("/admin").hasRole("ADMIN")
//			.antMatchers("/user").hasAnyRole("ADMIN", "USER")
//			.antMatchers("/").permitAll()
//			.and().formLogin();
		
		http.httpBasic().and().authorizeRequests()
		
			.antMatchers(HttpMethod.GET, "/imc/biometrias").hasAnyRole("ADMIN", "USER")
			.antMatchers(HttpMethod.POST, "/imc/usuarios").permitAll()
			.antMatchers(HttpMethod.GET, "/imc/usuarios").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/imc/usuarios/**").hasAnyRole("ADMIN", "USER")
			.anyRequest().denyAll()
		
			.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					
			.and()
				.csrf().disable();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		//return new BCryptPasswordEncoder();
		return NoOpPasswordEncoder.getInstance();
	}
	
	
}
