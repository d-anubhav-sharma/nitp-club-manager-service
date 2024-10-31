package com.nitp.club.management.services;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nitp.club.management.entities.EndpointData;
import com.nitp.club.management.repositories.EndpointDataRepository;

import jakarta.annotation.PostConstruct;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EndpointDataService {

	private static final Map<String,String> ALL_OPEN_ENDPOINTS = Map.of(
			"/user/createUser", "",
			"/auth/login", "",
			"/auth", "USER",
			"/auth/logout", "USER",
			"/user/me", "USER");
	private EndpointDataRepository endpointDataRepository;
	
	public EndpointDataService(EndpointDataRepository endpointDataRepository) {
		super();
		this.endpointDataRepository = endpointDataRepository;
	}
	
	@PostConstruct
	private void init() {
		ALL_OPEN_ENDPOINTS.entrySet().forEach(endpointEntry -> {
			String endpoint = endpointEntry.getKey();
			EndpointData endpointData = endpointDataRepository
					.findByEndpointPath(endpoint)
					.orElse(EndpointData.builder()
					.endpointPath(endpoint)
					.build());
			endpointData.setRequiredAuthority(endpointEntry.getValue());
			endpointDataRepository.save(endpointData);
		});
	}
}
