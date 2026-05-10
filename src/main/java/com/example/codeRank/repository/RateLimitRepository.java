package com.example.codeRank.repository;

import com.example.codeRank.model.RateLimit;
import com.example.codeRank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateLimitRepository extends JpaRepository<RateLimit, Long> {
    Optional<RateLimit> findByUser(User user);
}

