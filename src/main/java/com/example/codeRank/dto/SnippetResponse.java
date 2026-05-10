package com.example.codeRank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import com.example.codeRank.model.Language;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SnippetResponse {
    private Long id;
    private String title;
    private String description;
    private Language language;
    private String code;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

