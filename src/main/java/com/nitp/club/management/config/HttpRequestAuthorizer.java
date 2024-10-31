package com.nitp.club.management.config;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

import com.nitp.club.management.repositories.EndpointDataRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HttpRequestAuthorizer implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>{

	private EndpointDataRepository endpointDataRepository;
	
	public HttpRequestAuthorizer(EndpointDataRepository endpointDataRepository) {
		super();
		this.endpointDataRepository = endpointDataRepository;
	}

	@Override
	public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
		endpointDataRepository.findAll().forEach(endpointData -> {
			if(Strings.isBlank(endpointData.getRequiredAuthority())) {
				log.atInfo().log("Permit all for {}", endpointData.getEndpointPath());
				registry.requestMatchers(endpointData.getEndpointPath()).permitAll();
			}
			else {
				log.atInfo().log("Authenticated for path: {} authority: {}", endpointData.getEndpointPath(), endpointData.getRequiredAuthority());
				registry.requestMatchers(endpointData.getEndpointPath()).hasAuthority(endpointData.getRequiredAuthority());
			}
		});
		registry.anyRequest().authenticated();
	}

}
