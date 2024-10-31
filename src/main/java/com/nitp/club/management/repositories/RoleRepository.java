package com.nitp.club.management.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nitp.club.management.entities.CustomRole;

public interface RoleRepository extends JpaRepository<CustomRole, Long>{

	Optional<CustomRole> findByRoleName(String roleName);

}
