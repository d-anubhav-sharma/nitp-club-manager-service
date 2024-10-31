package com.nitp.club.management.services;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nitp.club.management.entities.CustomAuthority;
import com.nitp.club.management.entities.CustomRole;
import com.nitp.club.management.entities.CustomUser;
import com.nitp.club.management.models.CreateUserRequest;
import com.nitp.club.management.repositories.AuthorityRepository;
import com.nitp.club.management.repositories.RoleRepository;
import com.nitp.club.management.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public class UserService {

	private static final String USER_ROLE = "R_USER";
	private UserRepository userRepository;
	private PasswordEncoder encoder;
	private RoleRepository roleRepository;
	private AuthorityRepository authorityRepository;

	public UserService(UserRepository userRepository, 
			PasswordEncoder encoder,
			RoleRepository roleRepository,
			AuthorityRepository authorityRepository) {
		super();
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.roleRepository = roleRepository;
		this.authorityRepository = authorityRepository;
	}

	@PostConstruct
	private void init() {
		CustomAuthority authority = authorityRepository
				.findByAuthorityName("USER")
				.orElseGet(() -> authorityRepository.save(CustomAuthority.builder()
						.authorityName("USER")
						.build()));
		
		CustomRole role = roleRepository.findByRoleName(USER_ROLE)
				.orElseGet(() -> roleRepository.save(CustomRole.builder()
						.authorities(Set.of(authority))
						.roleName(USER_ROLE)
						.build()));
		
		CustomUser user = userRepository.findByUsername("root")
				.orElseGet(() -> createUser(CreateUserRequest.builder()
						.email("root.nitp@nitp.ac.in")
						.firstName("root")
						.lastName("nitp")
						.password("nitp_root@123")
						.username("root")
						.build(), Set.of(role)));
		log.atInfo().log("User created: {}", user);
	}
	
	public CustomUser createUser(CreateUserRequest request) {
		return userRepository.save(CustomUser.builder()
				.username(request.getUsername())
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.password(encoder.encode(request.getPassword()))
				.email(request.getEmail())
				.roles(defaultUserRole())
				.build());
	}
	
	private CustomUser createUser(CreateUserRequest request, Set<CustomRole> roles) {
		return userRepository.save(CustomUser.builder()
				.username(request.getUsername())
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.password(encoder.encode(request.getPassword()))
				.email(request.getEmail())
				.roles(roles)
				.build());
	}

	private Set<CustomRole> defaultUserRole() {
		return Set.of(roleRepository
				.findByRoleName(USER_ROLE).orElse(null));
	}

	public CustomUser findMe() {
		Object principal = SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		return userRepository.findByUsername(principal.toString()).orElse(null);
	}

	public List<CustomUser> findAll() {
		return userRepository.findAll();
	}
	
	
}
