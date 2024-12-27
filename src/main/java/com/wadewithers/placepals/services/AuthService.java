package com.wadewithers.placepals.services;

import com.wadewithers.placepals.models.User;
import com.wadewithers.placepals.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional(rollbackOn = Exception.class)
public class AuthService {

    private final UserRepo userRepo;


    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // Register a new user with hashed password
    public String registerUser(User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            return "Email is already in use.";
        }
        user.setPassword(user.getPassword()); // Hash password
        userRepo.save(user);
        return "User registered successfully.";
    }

    // Login user, check email and password match
    public String loginUser(User user) {
        User foundUser = userRepo.findByEmail(user.getEmail());
        if (foundUser == null || (!Objects.equals(user.getPassword(), foundUser.getPassword()))) {
            return "Invalid email or password.";
        }
        return "Login successful.";


//        // Check if user exists
//        User user = userRepository.findByEmail(email).orElse(null);
//        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//
//        // Return a success message or a token for further requests
//        return ResponseEntity.ok("Login successful");

    }

}
