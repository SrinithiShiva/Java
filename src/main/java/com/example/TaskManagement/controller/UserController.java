package com.example.TaskManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.TaskManagement.dto.UserRegistrationDTO;
import com.example.TaskManagement.dto.UserLoginDTO;
import com.example.TaskManagement.dto.UserProfileDTO;
import com.example.TaskManagement.dto.AuthResponseDTO;
import com.example.TaskManagement.entity.Users;
import com.example.TaskManagement.service.UserService;

import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.example.TaskManagement.security.JwtService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    /**
     * Register a new user account
     * POST /users/register
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        try {
            Users user = userService.registerUser(registrationDTO);
            UserProfileDTO profileDTO = convertToProfileDTO(user);
            AuthResponseDTO response = new AuthResponseDTO("User registered successfully", jwtService.generateToken(user), profileDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponseDTO(e.getMessage()));
        }
    }

    /**
     * Login user with credentials
     * POST /users/login
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@Valid @RequestBody UserLoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            Users user = userService.getUserByEmail(loginDTO.getEmail());
            UserProfileDTO profileDTO = convertToProfileDTO(user);
            AuthResponseDTO response = new AuthResponseDTO("Login successful", jwtService.generateToken(user), profileDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponseDTO(e.getMessage()));
        }
    }

    /**
     * Get user profile by ID
     * GET /users/{userId}
     */
    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        try {
            Users user = userService.getUserProfile(userId);
            UserProfileDTO profileDTO = convertToProfileDTO(user);
            return ResponseEntity.ok(profileDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthResponseDTO(e.getMessage()));
        }
    }

    /**
     * Update user profile
     * PUT /users/{userId}
     */
    @PutMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AuthResponseDTO> updateUserProfile(@PathVariable Long userId, @Valid @RequestBody UserProfileDTO profileDTO) {
        try {
            Users updatedUser = userService.updateUserProfile(userId, profileDTO);
            UserProfileDTO updatedProfileDTO = convertToProfileDTO(updatedUser);
            return ResponseEntity.ok(new AuthResponseDTO("Profile updated successfully", null, updatedProfileDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthResponseDTO(e.getMessage()));
        }
    }

    /**
     * Logout user
     * POST /users/{userId}/logout
     */
    @PostMapping("/{userId}/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AuthResponseDTO> logoutUser(@PathVariable Long userId) {
        try {
            userService.logoutUser(userId);
            return ResponseEntity.ok(new AuthResponseDTO("Logout successful"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponseDTO(e.getMessage()));
        }
    }

    /**
     * Helper method to convert Users entity to UserProfileDTO
     */
    private UserProfileDTO convertToProfileDTO(Users user) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }

}
