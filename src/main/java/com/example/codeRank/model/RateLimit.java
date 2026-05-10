package com.example.codeRank.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "rate_limits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "execution_count", nullable = false)
    private Integer executionCount = 0;

    @Column(name = "last_reset", nullable = false)
    private LocalDateTime lastReset;

    @PrePersist
    protected void onCreate() {
        if (lastReset == null) {
            lastReset = LocalDateTime.now();
        }
    }
}

