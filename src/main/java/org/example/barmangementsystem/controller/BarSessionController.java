package org.example.barmangementsystem.controller;

import org.example.barmangementsystem.entity.BarSession;
import org.example.barmangementsystem.entity.User;
import org.example.barmangementsystem.repository.BarSessionRepository;
import org.example.barmangementsystem.services.BarSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/session")
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")
public class BarSessionController {

    private final BarSessionService barSessionService;
    private final BarSessionRepository barSessionRepository;
    public BarSessionController(BarSessionService barSessionService, BarSessionRepository barSessionRepository) {
        this.barSessionService = barSessionService;
        this.barSessionRepository = barSessionRepository;
    }
    @GetMapping("/active")
    public ResponseEntity<BarSession> getActiveSession() {
        return barSessionRepository.findByClosedFalse()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
    @GetMapping("/current")
    public BarSession getCurrentSession() {
        return barSessionService.getCurrentSession();
    }

    @PostMapping("/open")
    public BarSession openSession(@RequestBody SessionOpenRequest req) {
        User user = new User();
        user.setId(req.userId()); // assume you pass userId from frontend
        return barSessionService.openSession(user, req.openingFloat());
    }

    @PostMapping("/close/{sessionId}")
    public BarSession closeSession(@PathVariable Long sessionId) {
        return barSessionService.closeSession(sessionId);
    }
    // 🟢 Get all closed sessions (history)
    @GetMapping("/history")
    public List<BarSession> getClosedSessions() {
        return barSessionRepository.findByClosedTrueOrderByClosingTimeDesc();
    }
    public record SessionOpenRequest(Long userId, BigDecimal openingFloat) {}
}

