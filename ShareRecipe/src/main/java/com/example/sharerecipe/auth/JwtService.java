package com.example.sharerecipe.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
	private final Key signingKey;
	private final Duration accessTtl;

	public JwtService(
			@Value("${app.jwt.secret}") String secret,
			@Value("${app.jwt.access-ttl}") Duration accessTtl
	) {
		this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
		this.accessTtl = accessTtl;
	}

	public String generateAccessToken(String subject, Set<String> roles) {
		Instant now = Instant.now();
		Instant expiresAt = now.plus(accessTtl);
		return Jwts.builder()
				.setSubject(subject)
				.claim("roles", roles)
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(expiresAt))
				.signWith(signingKey, SignatureAlgorithm.HS256)
				.compact();
	}

	public Instant getAccessTokenExpiry() {
		return Instant.now().plus(accessTtl);
	}

	public String extractSubject(String token) {
		return getClaims(token).getSubject();
	}

	public boolean isTokenValid(String token) {
		try {
			getClaims(token);
			return true;
		} catch (Exception ignored) {
			return false;
		}
	}

	private Claims getClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(signingKey)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
