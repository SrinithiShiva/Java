package com.example.codeRank.dto;

import com.example.codeRank.model.Language;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecutionResponse {
    private Boolean success;
    private String output;
    private String error;
    private Integer executionTime;
    private Integer memoryUsed;
    private Language language;
}

