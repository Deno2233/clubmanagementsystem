package org.example.barmangementsystem.controller;



import org.example.barmangementsystem.dto.AuthRequest;
import org.example.barmangementsystem.dto.AuthResponse;
import org.example.barmangementsystem.dto.RegisterRequest;
import org.example.barmangementsystem.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        try {
            authService.register(req);
            return ResponseEntity.ok().body("User registered successfully");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Registration failed");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        try {
            AuthResponse res = authService.login(req);
            return ResponseEntity.ok(res); // returns { token, username, role }
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(401).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Login failed");
        }
    }
}

