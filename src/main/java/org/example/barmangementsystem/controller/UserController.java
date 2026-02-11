package org.example.barmangementsystem.controller;


import org.example.barmangementsystem.entity.User;
import org.example.barmangementsystem.repository.UserRepository;
import org.example.barmangementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")
public class UserController {
private UserRepository userRepository;
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (user.getPasswordHash() == null || user.getPasswordHash().isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required");
        }

        String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);

        userRepository.save(user);
        return ResponseEntity.ok("User created");
    }

    @GetMapping
    public List<User> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}

