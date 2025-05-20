package com.bookstore.repository;

import com.bookstore.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepository extends AbstractFileRepository<User> {
    
    public UserRepository() {
        super(User.class, "users.json");
    }
    
    public Optional<User> findByUsername(String username) {
        return findAll().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }
    
    public Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
    
    public List<User> findByRole(String role) {
        return findAll().stream()
                .filter(user -> user.getRole().equals(role))
                .collect(Collectors.toList());
    }
    
    public boolean existsByUsername(String username) {
        return findAll().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }
    
    public boolean existsByEmail(String email) {
        return findAll().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }
}
