package com.nitp.club.management.controllers;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nitp.club.management.entities.Club;
import com.nitp.club.management.entities.ClubMember;
import com.nitp.club.management.models.CreateClubRequest;
import com.nitp.club.management.services.ClubService;

@RestController
@RestControllerAdvice
@RequestMapping("/club")
public class ClubController {

	private ClubService clubService;
	
	public ClubController(ClubService clubService) {
		super();
		this.clubService = clubService;
	}
	
	@PostMapping("/createClub")
	public Club createClub(@Validated @RequestBody CreateClubRequest createClubRequest) {
		return clubService.createClub(createClubRequest);
	}
	
	@GetMapping("/{clubId}/members")
	public List<ClubMember> getClubMembers(@PathVariable String clubId){
		return clubService.getClubMembers(Long.parseLong(clubId));
	}
}
