package org.example.barmangementsystem.services;


import org.example.barmangementsystem.entity.User;
import org.example.barmangementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User update(Long id, User updated) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setUsername(updated.getUsername());
                    existing.setPasswordHash(updated.getPasswordHash());
                    existing.setRole(updated.getRole());
                    return userRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
