package com.example.codeRank.controller;

import com.example.codeRank.dto.ExecutionRequest;
import com.example.codeRank.dto.ExecutionResponse;
import com.example.codeRank.model.Execution;
import com.example.codeRank.service.ExecutionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExecutionController {

    @Autowired
    private ExecutionService executionService;

    @PostMapping("/execute")
    public ResponseEntity<ExecutionResponse> executeCode(@Valid @RequestBody ExecutionRequest request) {
        ExecutionResponse response = executionService.executeCode(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Execution>> getHistory() {
        List<Execution> history = executionService.getUserExecutionHistory();
        return ResponseEntity.ok(history);
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<Execution> getExecutionById(@PathVariable Long id) {
        Execution execution = executionService.getExecutionById(id);
        return ResponseEntity.ok(execution);
    }
}

