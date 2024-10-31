package com.nitp.club.management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nitp.club.management.entities.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {

}
