package com.wadewithers.placepals.controller;

import com.wadewithers.placepals.models.User;
import com.wadewithers.placepals.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:3000")  // Allow CORS from your frontend
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        String response = authService.registerUser(user);
        if (response.equals("Email is already in use.")) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        String response = authService.loginUser(user);
        if (response.equals("Invalid email or password.")) {
            return ResponseEntity.status(401).body(response); // Unauthorized
        }
        return ResponseEntity.ok(response);
    }

}
