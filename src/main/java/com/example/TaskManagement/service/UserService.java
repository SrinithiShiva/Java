package com.example.TaskManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.TaskManagement.dto.UserRegistrationDTO;
import com.example.TaskManagement.dto.UserLoginDTO;
import com.example.TaskManagement.dto.UserProfileDTO;
import com.example.TaskManagement.entity.Users;
import com.example.TaskManagement.repository.UserRepository;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Register a new user
     */
    public Users registerUser(UserRegistrationDTO registrationDTO) {
        // Check if user already exists
        if (userRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Create new user
        Users user = new Users();
        user.setUserName(registrationDTO.getUserName());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setPhoneNumber(registrationDTO.getPhoneNumber());

        // Save and return user
        return userRepository.save(user);
    }

    /**
     * Authenticate user login
     */
    public Users loginUser(UserLoginDTO loginDTO) {
        Optional<Users> user = userRepository.findByEmail(loginDTO.getEmail());

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Users foundUser = user.get();

        // Verify password
        if (!passwordEncoder.matches(loginDTO.getPassword(), foundUser.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return foundUser;
    }

    /**
     * Get user profile by ID
     */
    public Users getUserProfile(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Update user profile
     */
    public Users updateUserProfile(Long userId, UserProfileDTO profileDTO) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (profileDTO.getUserName() != null) {
            user.setUserName(profileDTO.getUserName());
        }

        if (profileDTO.getEmail() != null) {
            user.setEmail(profileDTO.getEmail());
        }

        if (profileDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(profileDTO.getPhoneNumber());
        }

        return userRepository.save(user);
    }

    /**
     * Delete user (logout equivalent - can also be used to deactivate account)
     */
    public void logoutUser(Long userId) {
        // In a JWT-based system, logout is typically handled on client side
        // by removing the token. This method can be used for additional cleanup
        // or to track user sessions if needed.
    }
}
