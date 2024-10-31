package com.nitp.club.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nitp.club.management.filters.JwtAuthenticationFilter;
import com.nitp.club.management.repositories.EndpointDataRepository;

@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfig {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, 
			JwtAuthenticationFilter authenticationFilter, 
			EndpointDataRepository endpointDataRepository,
			HttpRequestAuthorizer authorizer) throws Exception {
		return http
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authorizer)
			.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}
}
