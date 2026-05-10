package com.example.codeRank.dto;

import com.example.codeRank.model.Language;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExecutionRequest {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Language must be one of: C, JAVA, PYTHON, JAVASCRIPT")
    private Language language;

    @NotBlank(message = "Code is required")
    private String code;

    private String input;
}

