package com.wadewithers.placepals.repo;

import com.wadewithers.placepals.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findById(Integer id);
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
