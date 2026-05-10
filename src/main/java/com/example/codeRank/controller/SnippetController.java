package com.example.codeRank.controller;

import com.example.codeRank.dto.SnippetRequest;
import com.example.codeRank.dto.SnippetResponse;
import com.example.codeRank.model.Language;
import com.example.codeRank.service.SnippetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/snippets")
public class SnippetController {

    @Autowired
    private SnippetService snippetService;

    @PostMapping
    public ResponseEntity<SnippetResponse> createSnippet(@Valid @RequestBody SnippetRequest request) {
        SnippetResponse response = snippetService.createSnippet(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllSnippets(@RequestParam(required = false) Language language) {
        List<SnippetResponse> snippets = snippetService.getAllSnippets(language);

        Map<String, Object> response = new HashMap<>();
        response.put("snippets", snippets);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SnippetResponse> getSnippetById(@PathVariable Long id) {
        SnippetResponse snippet = snippetService.getSnippetById(id);
        return ResponseEntity.ok(snippet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SnippetResponse> updateSnippet(@PathVariable Long id, @Valid @RequestBody SnippetRequest request) {
        SnippetResponse updated = snippetService.updateSnippet(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteSnippet(@PathVariable Long id) {
        snippetService.deleteSnippet(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Snippet deleted successfully");

        return ResponseEntity.ok(response);
    }
}

