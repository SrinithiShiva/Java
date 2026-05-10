package com.example.codeRank.service;

import com.example.codeRank.dto.SnippetRequest;
import com.example.codeRank.dto.SnippetResponse;
import com.example.codeRank.exception.ResourceNotFoundException;
import com.example.codeRank.model.CodeSnippet;
import com.example.codeRank.model.Language;
import com.example.codeRank.model.User;
import com.example.codeRank.repository.CodeSnippetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SnippetService {

    @Autowired
    private CodeSnippetRepository snippetRepository;

    @Autowired
    private UserService userService;

    public SnippetResponse createSnippet(SnippetRequest request) {
        User user = userService.getCurrentUser();

        CodeSnippet snippet = new CodeSnippet();
        snippet.setUser(user);
        snippet.setTitle(request.getTitle());
        snippet.setDescription(request.getDescription());
        snippet.setLanguage(request.getLanguage());
        snippet.setCode(request.getCode());

        CodeSnippet saved = snippetRepository.save(snippet);
        return mapToResponse(saved);
    }

    public List<SnippetResponse> getAllSnippets(Language language) {
        User user = userService.getCurrentUser();
        List<CodeSnippet> snippets;

        if (language != null) {
            snippets = snippetRepository.findByUserAndLanguageOrderByCreatedAtDesc(user, language);
        } else {
            snippets = snippetRepository.findByUserOrderByCreatedAtDesc(user);
        }

        return snippets.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public SnippetResponse getSnippetById(Long id) {
        User user = userService.getCurrentUser();
        CodeSnippet snippet = snippetRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Snippet not found"));
        return mapToResponse(snippet);
    }

    public SnippetResponse updateSnippet(Long id, SnippetRequest request) {
        User user = userService.getCurrentUser();
        CodeSnippet snippet = snippetRepository.findByIdAndUser(id, user).orElseThrow(() -> new ResourceNotFoundException("Snippet not found"));

        snippet.setTitle(request.getTitle());
        snippet.setDescription(request.getDescription());
        snippet.setCode(request.getCode());
        // Note: language cannot be updated

        CodeSnippet updated = snippetRepository.save(snippet);
        return mapToResponse(updated);
    }

    public void deleteSnippet(Long id) {
        User user = userService.getCurrentUser();
        CodeSnippet snippet = snippetRepository.findByIdAndUser(id, user).orElseThrow(() -> new ResourceNotFoundException("Snippet not found"));
        snippetRepository.delete(snippet);
    }

    private SnippetResponse mapToResponse(CodeSnippet snippet) {
        return SnippetResponse.builder()
                .id(snippet.getId())
                .title(snippet.getTitle())
                .description(snippet.getDescription())
                .language(snippet.getLanguage())
                .code(snippet.getCode())
                .createdAt(snippet.getCreatedAt())
                .updatedAt(snippet.getUpdatedAt())
                .build();
    }
}

