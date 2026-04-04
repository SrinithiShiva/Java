package com.example.sharerecipe.auth;

import com.example.sharerecipe.entity.Chef;
import com.example.sharerecipe.entity.VerificationToken;
import com.example.sharerecipe.repository.VerificationTokenRepository;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenService {
	private final VerificationTokenRepository verificationTokenRepository;
	private final Duration verificationTtl;

	public VerificationTokenService(VerificationTokenRepository verificationTokenRepository,
			@Value("${app.auth.verification-ttl}") Duration verificationTtl) {
		this.verificationTokenRepository = verificationTokenRepository;
		this.verificationTtl = verificationTtl;
	}

	public VerificationToken createToken(Chef chef) {
		VerificationToken token = new VerificationToken();
		token.setChef(chef);
		token.setToken(UUID.randomUUID().toString());
		token.setExpiresAt(Instant.now().plus(verificationTtl));
		return verificationTokenRepository.save(token);
	}

	public Optional<VerificationToken> findValidToken(String token) {
		return verificationTokenRepository.findByToken(token)
				.filter(vt -> vt.getUsedAt() == null)
				.filter(vt -> vt.getExpiresAt().isAfter(Instant.now()));
	}

	public void markUsed(VerificationToken token) {
		token.setUsedAt(Instant.now());
		verificationTokenRepository.save(token);
	}
}
