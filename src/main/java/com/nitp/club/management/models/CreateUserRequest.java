package com.nitp.club.management.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

	@Pattern(regexp = "[\\w.]{3,50}", message = "Username can be 3 to 50 characters and should contain alphanumeric character or underscore or dot")
	private String username;
	@Pattern(regexp = "[A-Za-z]{3,50}", message = "First name can be 3 to 50 characters and should contain english alphabets")
	private String firstName;
	@Pattern(regexp = "[A-Za-z]{3,50}", message = "Last name can be 3 to 50 characters and should contain english alphabets")
	private String lastName;
	@Email
	private String email;
	private String password;
	
}
