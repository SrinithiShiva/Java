package com.example.codeRank.service;

import com.example.codeRank.dto.UserStatsResponse;
import com.example.codeRank.exception.ResourceNotFoundException;
import com.example.codeRank.model.User;
import com.example.codeRank.repository.CodeSnippetRepository;
import com.example.codeRank.repository.ExecutionRepository;
import com.example.codeRank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExecutionRepository executionRepository;

    @Autowired
    private CodeSnippetRepository snippetRepository;

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserStatsResponse getUserStats() {
        User user = getCurrentUser();

        Long totalExecutions = executionRepository.countByUser(user);
        Long successfulExecutions = executionRepository.countByUserAndSuccess(user, true);
        Long failedExecutions = totalExecutions - successfulExecutions;

        Map<String, Long> languageUsage = new HashMap<>();
        List<Object[]> languageStats = executionRepository.countByUserGroupByLanguage(user);
        for (Object[] stat : languageStats) {
            languageUsage.put((String) stat[0], (Long) stat[1]);
        }

        Long totalSnippets = (long) snippetRepository.findByUserOrderByCreatedAtDesc(user).size();
        Double avgExecutionTime = executionRepository.getAverageExecutionTimeByUser(user);

        return UserStatsResponse.builder()
                .totalExecutions(totalExecutions)
                .successfulExecutions(successfulExecutions)
                .failedExecutions(failedExecutions)
                .languageUsage(languageUsage)
                .totalSnippets(totalSnippets)
                .averageExecutionTime(avgExecutionTime != null ? avgExecutionTime : 0.0)
                .build();
    }
}

