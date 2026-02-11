package org.example.barmangementsystem.controller;



import org.example.barmangementsystem.entity.StockMovement;
import org.example.barmangementsystem.entity.StockMovementType;
import org.example.barmangementsystem.repository.StockMovementRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stockout")
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")
public class StockOutController {

    private final StockMovementRepository repo;

    public StockOutController(StockMovementRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<StockMovement> getAllStockOut() {
        return repo.findByTypeOrderByDateDesc(StockMovementType.OUT);
    }

    @PostMapping
    public StockMovement addStockOut(@RequestBody StockMovement movement) {
        movement.setType(StockMovementType.OUT);
        return repo.save(movement);
    }
}
