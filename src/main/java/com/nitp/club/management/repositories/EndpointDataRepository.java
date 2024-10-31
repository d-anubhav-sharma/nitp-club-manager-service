package com.nitp.club.management.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nitp.club.management.entities.EndpointData;

public interface EndpointDataRepository extends JpaRepository<EndpointData, Long> {

	Optional<EndpointData> findByEndpointPath(String requestedPath);

}
