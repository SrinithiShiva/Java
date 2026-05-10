package com.example.codeRank.dto;

import com.example.codeRank.model.Language;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SnippetRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Language must be one of: C, JAVA, PYTHON, JAVASCRIPT")
    private Language language;

    @NotBlank(message = "Code is required")
    private String code;
}

