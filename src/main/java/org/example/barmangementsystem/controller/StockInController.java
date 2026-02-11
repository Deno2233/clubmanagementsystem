package org.example.barmangementsystem.controller;



import org.example.barmangementsystem.entity.StockMovement;
import org.example.barmangementsystem.entity.StockMovementType;
import org.example.barmangementsystem.repository.StockMovementRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stockin")
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")
public class StockInController {

    private final StockMovementRepository repo;

    public StockInController(StockMovementRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<StockMovement> getAllStockIn() {
        return repo.findByTypeOrderByDateDesc(StockMovementType.IN);
    }

    @PostMapping
    public StockMovement addStockIn(@RequestBody StockMovement movement) {
        movement.setType(StockMovementType.IN);
        return repo.save(movement);
    }
}
