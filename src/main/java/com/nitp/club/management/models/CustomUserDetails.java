package com.nitp.club.management.models;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nitp.club.management.entities.CustomUser;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class CustomUserDetails implements UserDetails{

	private static final long serialVersionUID = -6648938363454001484L;

	private transient CustomUser user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user
			.getRoles()
			.stream()
			.flatMap(role -> role
					.getAuthorities()
					.stream())
			.map(auth -> new SimpleGrantedAuthority(auth.getAuthorityName()))
			.toList();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	public CustomUserDetails(CustomUser user) {
		super();
		this.user = user;
	}

}
