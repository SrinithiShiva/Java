package com.example.codeRank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStatsResponse {
    private Long totalExecutions;
    private Long successfulExecutions;
    private Long failedExecutions;
    private Map<String, Long> languageUsage;
    private Long totalSnippets;
    private Double averageExecutionTime;
}

