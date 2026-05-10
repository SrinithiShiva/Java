package com.example.codeRank.service;

import com.example.codeRank.exception.RateLimitExceededException;
import com.example.codeRank.model.RateLimit;
import com.example.codeRank.model.User;
import com.example.codeRank.repository.RateLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class RateLimitService {

    @Autowired
    private RateLimitRepository rateLimitRepository;

    @Value("${ratelimit.executions.per.hour}")
    private int maxExecutionsPerHour;

    @Transactional
    public void checkRateLimit(User user) {
        RateLimit rateLimit = rateLimitRepository.findByUser(user).orElseGet(() -> createNewRateLimit(user));

        // Reset counter if more than 1 hour has passed
        LocalDateTime oneHourAgo = LocalDateTime.now().minus(1, ChronoUnit.HOURS);
        if (rateLimit.getLastReset().isBefore(oneHourAgo)) {
            rateLimit.setExecutionCount(0);
            rateLimit.setLastReset(LocalDateTime.now());
        }

        // Check if limit exceeded
        if (rateLimit.getExecutionCount() >= maxExecutionsPerHour) {
            throw new RateLimitExceededException("Rate limit exceeded. Maximum " + maxExecutionsPerHour + " executions per hour allowed.");
        }

        // Increment counter
        rateLimit.setExecutionCount(rateLimit.getExecutionCount() + 1);
        rateLimitRepository.save(rateLimit);
    }

    private RateLimit createNewRateLimit(User user) {
        RateLimit rateLimit = new RateLimit();
        rateLimit.setUser(user);
        rateLimit.setExecutionCount(0);
        rateLimit.setLastReset(LocalDateTime.now());
        return rateLimitRepository.save(rateLimit);
    }
}

