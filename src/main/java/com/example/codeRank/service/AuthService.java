package com.example.codeRank.service;

import com.example.codeRank.dto.LoginRequest;
import com.example.codeRank.dto.LoginResponse;
import com.example.codeRank.dto.RegisterRequest;
import com.example.codeRank.model.User;
import com.example.codeRank.repository.UserRepository;
import com.example.codeRank.config.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new IllegalArgumentException("Username not found"));

        if (user == null) {
            throw new IllegalArgumentException("Username not found");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        String token = jwtTokenProvider.generateToken(user);
        return new LoginResponse(token, request.getUsername(), jwtExpiration / 1000);
    }
}

