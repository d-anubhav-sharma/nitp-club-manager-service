package com.nitp.club.management.services;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenService {

	@Value("${jwt.secretkey}")
	private String secretkey;
	@Value("${jwt.expirationTime}")
	private String expirationTime;

	private JwtBuilder builder() {
		return Jwts
				.builder()
				.setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(Instant.now().plusSeconds(Long.parseLong(expirationTime))))
				.compressWith(CompressionCodecs.GZIP)
				.setNotBefore(Date.from(Instant.now()))
				.setSubject("nitp-club-management-login-jwt")
				.signWith(SignatureAlgorithm.HS512, secretkey);
	}
	
	private JwtParser parser() {
		return Jwts
				.parser()
				.setSigningKey(secretkey)
				.setCompressionCodecResolver(header -> CompressionCodecs.GZIP);
	}

	public Map<String, Object> parseToken(String token) {
		return parser().parseClaimsJws(token).getBody();
	}
	

	public String generateToken(Map<String, Object> claims) {
		return builder()
				.addClaims(claims)
				.compact();
	}
}
