package com.Leonardo168.api.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfigV2 {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.httpBasic()
				.and()
				.authorizeHttpRequests()
				.requestMatchers(HttpMethod.GET, "/user").permitAll()
				.requestMatchers(HttpMethod.POST, "/user").permitAll()
				.requestMatchers(HttpMethod.GET, "/parking-spot/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/parking-spot").hasAnyRole("ADMIN", "USER")
				.requestMatchers(HttpMethod.PUT, "/parking-spot/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/parking-spot/**").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and().csrf().disable();
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
