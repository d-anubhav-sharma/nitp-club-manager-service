package com.nitp.club.management.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nitp.club.management.entities.Club;
import com.nitp.club.management.entities.ClubMember;
import com.nitp.club.management.entities.CustomUser;
import com.nitp.club.management.exceptions.UserNotFoundException;
import com.nitp.club.management.models.CreateClubRequest;
import com.nitp.club.management.repositories.ClubRepository;
import com.nitp.club.management.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public class ClubService {

	private ClubRepository clubRepository;
	private UserRepository userRepository;
	
	public Club createClub(CreateClubRequest createClubRequest) {
		Optional<CustomUser> optionalUser = userRepository.findByUsername(createClubRequest.getClubOwnerUsername());
		if(optionalUser.isEmpty()) {
			log.atError().log("User not found: ", createClubRequest.getClubOwnerUsername());
			throw new UserNotFoundException(createClubRequest.getClubOwnerUsername());
		}
		return clubRepository.save(Club.builder()
				.clubName(createClubRequest.getClubName())
				.clubOwner(optionalUser.get())
				.build()
				);
	}

	public List<ClubMember> getClubMembers(long parseLong) {
		return new ArrayList<>();
	}
}
