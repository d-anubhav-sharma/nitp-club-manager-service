package com.nitp.club.management.filters;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nitp.club.management.models.AuthData;
import com.nitp.club.management.services.AuthenticationService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private AuthenticationService authenticationService;
	
	public JwtAuthenticationFilter(AuthenticationService authenticationService) {
		super();
		this.authenticationService = authenticationService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.atInfo().log("Request for path: {}", request.getRequestURI());
		if(authenticationService.isPublicResource(request)) {
			log.atInfo().log("Public path, giving default access");
			filterChain.doFilter(request, response);
			return;
		}
		
		AuthData authData = authenticationService.extractAuthData(request);
		log.atInfo().log("Auth Data received: {}", authData);
		
		if(!authenticationService.isLoggedIn(authData)) {
			log.atInfo().log("User is not logged in ");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return;
		}
		
		if(!authenticationService.hasRequiredPrivilege(authData)) {
			log.atInfo().log("User is not allowed to access the resource");
			response.setStatus(HttpStatus.FORBIDDEN.value());
			return;
		}
		List<SimpleGrantedAuthority> authorities = authData.getAuthorityNames().stream().map(SimpleGrantedAuthority::new).toList();
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				authData.getUsername(), authData.getToken(), authorities);
		authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authToken);
		log.atInfo().log("Providing the resource to the user");
		filterChain.doFilter(request, response);
		
	}

}
