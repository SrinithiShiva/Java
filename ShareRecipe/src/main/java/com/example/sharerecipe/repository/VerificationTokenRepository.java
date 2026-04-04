package com.example.sharerecipe.repository;

import com.example.sharerecipe.entity.VerificationToken;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {
	Optional<VerificationToken> findByToken(String token);
}
