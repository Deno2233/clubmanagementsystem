package org.example.barmangementsystem.controller;

import org.example.barmangementsystem.entity.Product;
import org.example.barmangementsystem.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // --- Create ---
    /**
     * POST /api/products
     * Body: { "name":"Beer", "price":150.00, "stockQuantity":20, "category":{"id":1} }
     */
    @PostMapping
    public String saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    // --- Read ---
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @GetMapping("/out-of-stock")
    public List<Product> getOutOfStockProducts() {
        return productService.getOutOfStockProducts();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    // --- Update ---
    /**
     * PUT /api/products/{id}
     * Body: { "name":"Wine", "price":250.00, "stockQuantity":30, "category":{"id":2} }
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @RequestBody Product product) {
        try {
            Product updated = productService.updateProduct(id, product);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- Delete ---
    /**
     * DELETE /api/products/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}