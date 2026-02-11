package org.example.barmangementsystem.services;


import org.example.barmangementsystem.dto.AuthRequest;
import org.example.barmangementsystem.dto.AuthResponse;
import org.example.barmangementsystem.dto.JwtUtil;
import org.example.barmangementsystem.dto.RegisterRequest;
import org.example.barmangementsystem.entity.User;
import org.example.barmangementsystem.entity.UserPermission;
import org.example.barmangementsystem.repository.UserPermissionRepository;
import org.example.barmangementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
@Autowired
private UserPermissionRepository permissionRepository;
    @Autowired
    private JwtUtil jwtUtil;

    public String register(RegisterRequest req) throws IllegalArgumentException {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        User u = new User();
        u.setUsername(req.getUsername());
        u.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        u.setRole(req.getRole() == null ? "CASHIER" : req.getRole());

        userRepository.save(u);
        return "registered";
    }

    public AuthResponse login(AuthRequest req) throws IllegalArgumentException {
        Optional<User> userOpt = userRepository.findByUsername(req.getUsername());
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        User u = userOpt.get();
        if (!passwordEncoder.matches(req.getPassword(), u.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(u.getUsername(), u.getRole(), u.getId() );

// ✅ Fetch allowed modules from repository
        List<String> modules = permissionRepository.findByUserId(u.getId())
                .stream()
                .filter(UserPermission::isCanAccess)
                .map(UserPermission::getModuleName)
                .toList();

// ✅ Include permissions in response
        return new AuthResponse(token, u.getUsername(),u.getId(), u.getRole(), modules);

    }
}
