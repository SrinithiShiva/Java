package com.example.codeRank.service;

import com.example.codeRank.dto.ExecutionRequest;
import com.example.codeRank.dto.ExecutionResponse;
import com.example.codeRank.model.Execution;
import com.example.codeRank.model.User;
import com.example.codeRank.repository.ExecutionRepository;
import com.example.codeRank.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExecutionService {

    @Autowired
    private ExecutionRepository executionRepository;

    @Autowired
    private DockerExecutionService dockerExecutionService;

    @Autowired
    private RateLimitService rateLimitService;

    @Autowired
    private UserService userService;

    public ExecutionResponse executeCode(ExecutionRequest request) {
        User user = userService.getCurrentUser();

        // Check rate limit
        rateLimitService.checkRateLimit(user);

        // Validate code size
        if (request.getCode().length() > 10 * 1024) {
            throw new IllegalArgumentException("Code size exceeds 10KB limit");
        }

        // Execute code using Docker
        DockerExecutionService.ExecutionResult result = dockerExecutionService.executeCode(
                request.getLanguage(),
                request.getCode(),
                request.getInput()
        );

        // Save execution to database
        Execution execution = new Execution();
        execution.setUser(user);
        execution.setLanguage(request.getLanguage());
        execution.setCode(request.getCode());
        execution.setInput(request.getInput());
        execution.setOutput(result.getOutput());
        execution.setError(result.getError());
        execution.setSuccess(result.isSuccess());
        execution.setExecutionTimeMs(result.getExecutionTime());
        execution.setMemoryUsedMb(result.getMemoryUsed());
        executionRepository.save(execution);

        return ExecutionResponse.builder()
                .success(result.isSuccess())
                .output(result.getOutput())
                .error(result.getError())
                .executionTime(result.getExecutionTime())
                .memoryUsed(result.getMemoryUsed())
                .language(request.getLanguage())
                .build();
    }

    public List<Execution> getUserExecutionHistory() {
        User user = userService.getCurrentUser();
        return executionRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public Execution getExecutionById(Long id) {
        User user = userService.getCurrentUser();
        return executionRepository.findByIdAndUser(id, user).orElseThrow(() -> new ResourceNotFoundException("Execution not found"));
    }
}

