package com.example.sharerecipe.auth;

import com.example.sharerecipe.entity.Chef;
import com.example.sharerecipe.entity.RefreshToken;
import com.example.sharerecipe.repository.RefreshTokenRepository;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {
	private final RefreshTokenRepository refreshTokenRepository;
	private final Duration refreshTtl;

	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
			@Value("${app.jwt.refresh-ttl}") Duration refreshTtl) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.refreshTtl = refreshTtl;
	}

	public RefreshToken createToken(Chef chef) {
		RefreshToken token = new RefreshToken();
		token.setChef(chef);
		token.setToken(UUID.randomUUID().toString());
		token.setExpiresAt(Instant.now().plus(refreshTtl));
		return refreshTokenRepository.save(token);
	}

	public Optional<RefreshToken> findValidToken(String token) {
		return refreshTokenRepository.findByToken(token)
				.filter(rt -> !rt.isRevoked())
				.filter(rt -> rt.getExpiresAt().isAfter(Instant.now()));
	}

	public void revoke(RefreshToken token) {
		token.setRevoked(true);
		refreshTokenRepository.save(token);
	}
}
