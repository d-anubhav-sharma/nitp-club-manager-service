package com.nitp.club.management.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nitp.club.management.entities.CustomAuthority;

public interface AuthorityRepository extends JpaRepository<CustomAuthority, Long>{

	Optional<CustomAuthority> findByAuthorityName(String authorityName);

}
