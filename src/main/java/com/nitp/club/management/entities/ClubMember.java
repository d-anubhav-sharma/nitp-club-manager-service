package com.nitp.club.management.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ClubMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String memberName;
	private String coolName;
	private String username;
}
