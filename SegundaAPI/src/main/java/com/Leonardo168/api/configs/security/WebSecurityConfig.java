//package com.Leonardo168.api.configs.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
////@Configuration
//public class WebSecurityConfig{
//	
//	@Autowired
//	UserDetailsServiceImpl userDetailsService;
//	
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http
//			.httpBasic()
//			.and()
//			.authorizeHttpRequests()
//			.requestMatchers(HttpMethod.GET, "/parking-spot/**").permitAll()
//			.requestMatchers(HttpMethod.POST, "/parking-spot").hasAnyRole("ADMIN", "USER")
//			.requestMatchers(HttpMethod.PUT, "/parking-spot/**").hasRole("ADMIN")
//			.requestMatchers(HttpMethod.DELETE, "/parking-spot/**").hasRole("ADMIN")
//			.anyRequest().authenticated()
//			.and()
//			.csrf().disable();
//		return http.build();
//	}
//	
//	@Bean
//	SecurityConfigurerAdapter<AuthenticationManager, AuthenticationManagerBuilder> authenticationConfigurer(){
//		return new SecurityConfigurerAdapter<AuthenticationManager, AuthenticationManagerBuilder>() {
//			@Override
//			public void configure(AuthenticationManagerBuilder auth) throws Exception {
//				auth.userDetailsService(userDetailsService)
//					.passwordEncoder(passwordEncoder());
//			}
//		};
//	}
//	
//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//}
