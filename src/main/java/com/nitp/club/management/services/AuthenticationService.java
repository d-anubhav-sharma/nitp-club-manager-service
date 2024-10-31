package com.nitp.club.management.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nitp.club.management.config.NITPConstants;
import com.nitp.club.management.entities.CustomRole;
import com.nitp.club.management.entities.CustomUser;
import com.nitp.club.management.entities.EndpointData;
import com.nitp.club.management.exceptions.AlreadyLoggedOutException;
import com.nitp.club.management.exceptions.IncorrectPasswordException;
import com.nitp.club.management.exceptions.UserNotFoundException;
import com.nitp.club.management.models.AuthData;
import com.nitp.club.management.models.LoginRequest;
import com.nitp.club.management.models.LoginResponse;
import com.nitp.club.management.repositories.EndpointDataRepository;
import com.nitp.club.management.repositories.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public class AuthenticationService {

	private UserRepository userRepository;
	private EndpointDataRepository endpointDataRepository;
	private JwtTokenService jwtTokenService;
	private PasswordEncoder encoder;
	@Value("${jwt.expirationTime}")
	private String expirationTime;

	public AuthenticationService(EndpointDataRepository endpointDataRepository, JwtTokenService jwtTokenService,
			UserRepository userRepository, PasswordEncoder encoder) {
		super();
		this.encoder = encoder;
		this.userRepository = userRepository;
		this.jwtTokenService = jwtTokenService;
		this.endpointDataRepository = endpointDataRepository;
	}

	public boolean isLoggedIn(AuthData authData) {
		return !Strings.isBlank(authData.getUsername());
	}

	public boolean hasRequiredPrivilege(AuthData authData) {
		return authData.getAuthorityNames().contains(authData.getRequiredAuthority());
	}

	public AuthData extractAuthData(HttpServletRequest request) {
		Cookie authCookie = findAuthCookie(request);
		if(authCookie==null) return new AuthData();
		String token = authCookie.getValue();
		Map<String, Object> claims = jwtTokenService.parseToken(token);
		String username = claims.getOrDefault(NITPConstants.USERNAME, "").toString();

		log.atInfo().log("Auth cookie received for username: {}", username);
		Optional<CustomUser> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isEmpty())
			throw new UserNotFoundException(username);

		String requiredPriv = endpointDataRepository
				.findByEndpointPath(request.getRequestURI().replace("/nitp-club-management/api", ""))
				.orElse(new EndpointData())
				.getRequiredAuthority();

		CustomUser user = optionalUser.get();
		return AuthData
				.builder()
				.token(token)
				.username(username)
				.email(user.getEmail())
				.requiredAuthority(requiredPriv)
				.requestedPath(request.getRequestURI())
				.authorityNames(getAuthorities(user))
				.build();
	}

	public boolean isPublicResource(HttpServletRequest request) {
		return Strings.isBlank(endpointDataRepository
				.findByEndpointPath(request.getRequestURI().replace("/nitp-club-management/api", ""))
				.orElse(new EndpointData()).getRequiredAuthority());
	}

	private Cookie findAuthCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies==null) return null;
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals(NITPConstants.AUTH_COOKIE)) {
				return cookie;
			}
		}
		return null;
	}

	private List<String> getAuthorities(CustomUser user) {
		List<String> authorities = new ArrayList<>();
		if (user.getRoles() == null || user.getRoles().isEmpty())
			return authorities;
		Set<CustomRole> roles = user.getRoles();
		roles.stream().forEach(role -> {
			if (role.getAuthorities() != null) {
				role.getAuthorities().forEach(auth -> authorities.add(auth.getAuthorityName()));
			}
		});
		return authorities;
	}

	public String logout(HttpServletRequest request, HttpServletResponse response) {
		AuthData authData = extractAuthData(request);
		if (Strings.isBlank(authData.getUsername()))
			throw new AlreadyLoggedOutException();
		Cookie cookie = new Cookie(NITPConstants.AUTH_COOKIE, "");
		cookie.setDomain("localhost");
		cookie.setPath("/");
		cookie.setMaxAge(0);
		cookie.setValue("");
		response.addCookie(cookie);
		return "success";
	}

	public LoginResponse login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
		log.atInfo().log("Domain: {}", request.getServerName());
		Optional<CustomUser> optionalUser = userRepository.findByUsername(loginRequest.getUsername());
		if (optionalUser.isEmpty()) {
			throw new UsernameNotFoundException(loginRequest.getUsername());
		}
		CustomUser user = optionalUser.get();
		if (!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new IncorrectPasswordException();
		}
		String token = jwtTokenService.generateToken(Map.of(NITPConstants.USERNAME, loginRequest.getUsername(),
				"authorities", user.getRoles().stream().flatMap(role -> role.getAuthorities().stream()).toList()));
		Cookie cookie = new Cookie(NITPConstants.AUTH_COOKIE, token);
		cookie.setDomain(request.getServerName());
		cookie.setPath("/");
		cookie.setMaxAge(Integer.parseInt(expirationTime));
		cookie.setValue(token);
		response.addCookie(cookie);
		return LoginResponse.builder().username(loginRequest.getUsername()).token(token).build();

	}

}
