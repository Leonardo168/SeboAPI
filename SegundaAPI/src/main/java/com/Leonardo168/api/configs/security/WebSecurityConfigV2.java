package com.Leonardo168.api.configs.security;

import static org.springframework.security.config.Customizer.withDefaults;

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
	        .httpBasic(withDefaults())
	        .authorizeHttpRequests(authorizeRequests ->
	            authorizeRequests
	                .requestMatchers(HttpMethod.GET, "/user").hasRole("ADMIN")
	                .requestMatchers(HttpMethod.POST, "/user").permitAll()
	                .requestMatchers(HttpMethod.PUT, "/user").hasAnyRole("ADMIN", "USER")
	                .requestMatchers(HttpMethod.PUT, "/user/admin/{id}").hasRole("ADMIN")
	                .requestMatchers(HttpMethod.DELETE, "/user/self").hasRole("USER")
	                .requestMatchers(HttpMethod.DELETE, "/user/{id}").hasRole("ADMIN")
	                .requestMatchers(HttpMethod.DELETE, "/user/definitivo/{id}").hasRole("ADMIN")
	                
	                .requestMatchers(HttpMethod.GET, "/parking-spot/{id}").hasAnyRole("ADMIN", "USER")
	                .requestMatchers(HttpMethod.POST, "/parking-spot").hasAnyRole("ADMIN", "USER")
	                .requestMatchers(HttpMethod.PUT, "/parking-spot/{id}").hasRole("ADMIN")
	                .requestMatchers(HttpMethod.DELETE, "/parking-spot/{id}").hasRole("ADMIN")
	                
	                .anyRequest().authenticated()
	        )
	        .csrf(csrf -> csrf.disable());
	    return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
