package com.wadewithers.placepals.controller;

import com.wadewithers.placepals.models.User;
import com.wadewithers.placepals.models.UserLoginRequest;
import com.wadewithers.placepals.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.wadewithers.placepals.constants.Constants.PHOTO_DIRECTORY;
import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/profiles")
@CrossOrigin(origins = "http://localhost:3000")  // Allow CORS from your frontend
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    //System.out.println(userRepository.findAll());
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.created(URI.create("/profiles/userID")).body(userService.createUserProfile(user));
    }

    @GetMapping
    public ResponseEntity<Page<User>> getProfiles(@RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(userService.getAllProfiles(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getProfile(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @PutMapping
    public ResponseEntity<String> uploadPhoto(@RequestParam("id") String id, @RequestParam("file")MultipartFile file) {
        return ResponseEntity.ok().body(userService.uploadPhoto(id, file));
    }

    @GetMapping(path = "/image/{filename}", produces = { IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE })
    public byte[] getPhoto(@PathVariable("filename") String filename) throws IOException {
        return Files.readAllBytes(Paths.get(PHOTO_DIRECTORY + filename));
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        if (userService.userExistsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(null); // User already exists
        }
        User createdUser = userService.createUserProfile(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest loginRequest) {
        boolean isAuthenticated = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
        if (isAuthenticated) {
            //String token = jwtTokenProvider.generateToken(loginRequest.getEmail()); // Generate JWT token
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @GetMapping("/me/{email}")
//    @PostMapping("/me")
//    @PutMapping("/me")
    public ResponseEntity<User> getCurrentUserProfile(@PathVariable(value = "email") String email) {
        // Get the currently logged-in user
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userId = authentication.getName(); // Assuming username is the principal (or use the ID if it's stored as principal)

        // Fetch user details using the username (or userId)
        //User user = userService.getUserByEmail(email);

        return ResponseEntity.ok().body(userService.getUserByEmail(email));
    }

}
