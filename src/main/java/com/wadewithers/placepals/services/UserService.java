package com.wadewithers.placepals.services;

import com.wadewithers.placepals.models.User;
import com.wadewithers.placepals.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.wadewithers.placepals.constants.Constants.PHOTO_DIRECTORY;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    Long uid = 3L;
    public Page<User> getAllProfiles(int page, int size) {
        Page<User> users = userRepo.findAll(PageRequest.of(page, size, Sort.by("budget")));

        // Print the size of the page and users on that page
        System.out.println("Fetched " + users.getContent().size() + " users for page " + page + " with size " + size);
        users.getContent().forEach(user -> System.out.println("Fetched user: " + user));

        return users;//userRepo.findAll(PageRequest.of(page, size, Sort.by("firstName")));
    }

    public User getUser(String id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByEmail(String email) {
        // Fetch the user from the database
        return userRepo.findByEmail(email);
                //.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User createUserProfile(User user) {
        return userRepo.save(user);
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    /*
    Upload user photo by user id
     */
    public String uploadPhoto(String id, MultipartFile file) {
        //log.info("Saving picture for user ID: {}", id);
        User user = getUser(id);
        String photoUrl = getUserPhoto.apply(id, file);
        user.setProfilePictureUrl(photoUrl);
        userRepo.save(user);
        return photoUrl;
    }

    /*
    Get file extension of user's photo.
    * Filter to name with . in it
    * Map . + substring that comes after .
    * Add png extension if unable to retrieve extension
     */
    private final Function<String, String> fileExtension = filename -> Optional.of(filename)
            .filter(name -> name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") + 1))
            .orElse(".png");

    /*
    Get user photo
     */
    private final BiFunction<String, MultipartFile, String> getUserPhoto = (id, image) -> {

        // Name the file from user id and file extension.
        String filename = id + fileExtension.apply(image.getOriginalFilename());

        try {
            // Get location of file on computer from photo directory.
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();

            // Create the location if it does not already exist.
            if (!Files.exists(fileStorageLocation)) { Files.createDirectories(fileStorageLocation); }

            // Save the file in specified location and replace file if it already exists.
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(filename), REPLACE_EXISTING);

            // Return the string of the url path to the file.
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/profiles/image/" + filename).toUriString();
        } catch (Exception exception) {
            throw new RuntimeException("Unable to save image");
        }
    };

    public boolean userExistsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    public boolean authenticateUser(String email, String password) {
        User user = userRepo.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return true; // Simple password match (use hashing in production)
        }
        return false;
    }

}

