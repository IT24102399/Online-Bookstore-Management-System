package com.bookstore.service;

import com.bookstore.model.Customer;
import com.bookstore.model.User;
import com.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
    
    public List<Customer> getAllCustomers() {
        return userRepository.findByRole("CUSTOMER").stream()
                .map(user -> (Customer) user)
                .collect(Collectors.toList());
    }
    
    public List<Admin> getAllAdmins() {
        return userRepository.findByRole("ADMIN").stream()
                .map(user -> (Admin) user)
                .collect(Collectors.toList());
    }
    
    public List<Vendor> getAllVendors() {
        return userRepository.findByRole("VENDOR").stream()
                .map(user -> (Vendor) user)
                .collect(Collectors.toList());
    }
    
    public List<DeliveryAgent> getAllDeliveryAgents() {
        return userRepository.findByRole("DELIVERY_AGENT").stream()
                .map(user -> (DeliveryAgent) user)
                .collect(Collectors.toList());
    }
    
    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }
    
    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }
    
    public Optional<User> authenticateUser(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));
    }
}
