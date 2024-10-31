package com.nitp.club.management.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClubRequest {

	private long id;
	private String clubName;
	private String clubOwnerUsername;
	
}
