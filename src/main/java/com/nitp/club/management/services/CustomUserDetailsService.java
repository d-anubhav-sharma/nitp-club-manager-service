package com.nitp.club.management.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nitp.club.management.entities.CustomUser;
import com.nitp.club.management.models.CustomUserDetails;
import com.nitp.club.management.repositories.UserRepository;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CustomUserDetailsService implements UserDetailsService{

	private UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<CustomUser> user = userRepository.findByUsername(username);
		if(user.isEmpty()) throw new UsernameNotFoundException(username);
		return new CustomUserDetails(user.get());
	}

}
