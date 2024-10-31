package com.nitp.club.management.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nitp.club.management.entities.CustomUser;

public interface UserRepository extends JpaRepository<CustomUser, Long> {

	Optional<CustomUser> findByUsername(String username);

}
