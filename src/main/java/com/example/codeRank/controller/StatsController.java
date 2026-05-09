package com.example.codeRank.controller;

import com.example.codeRank.dto.UserStatsResponse;
import com.example.codeRank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserStatsResponse> getUserStats() {
        UserStatsResponse stats = userService.getUserStats();
        return ResponseEntity.ok(stats);
    }
}

