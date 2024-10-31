package com.nitp.club.management.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthData {

	private String username;
	private String token;
	private List<String> authorityNames;
	private String email;
	private String requestedPath;
	private String requiredAuthority;
	
}
