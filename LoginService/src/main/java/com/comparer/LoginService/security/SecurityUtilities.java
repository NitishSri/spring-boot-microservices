package com.comparer.LoginService.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class SecurityUtilities {

	private final static SecurityUtilities instance = new SecurityUtilities();

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	public String generateToken(UserDetails userDetails, String jwtTokenKey) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername(), jwtTokenKey);
	}

	private String doGenerateToken(Map<String, Object> claims, String subject, String jwtTokenKey) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, jwtTokenKey.getBytes()).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails, String jwtTokenKey) {
		final String username = Jwts.parser().setSigningKey(jwtTokenKey.getBytes()).parseClaimsJws(token).getBody()
				.getSubject();
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, jwtTokenKey));
	}

	private Boolean isTokenExpired(String token, String jwtTokenKey) {
		final Date expiration = Jwts.parser().setSigningKey(jwtTokenKey.getBytes()).parseClaimsJws(token).getBody()
				.getExpiration();
		return expiration.before(new Date());
	}

	public static SecurityUtilities getInstance() {
		return instance;
	}

}
