package org.example.barmangementsystem.controller;

import org.example.barmangementsystem.entity.Category;
import org.example.barmangementsystem.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Create or update a category.
     * Example: POST /api/categories
     * Body: { "name": "Beer" }
     */
    @PostMapping
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        Category saved = categoryService.saveCategory(category);
        return ResponseEntity.ok(saved);
    }

    /**
     * Get all categories.
     * Example: GET /api/categories
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * Get a category by its ID.
     * Example: GET /api/categories/5
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get a category by name.
     * Example: GET /api/categories/by-name?name=Beer
     */

    /**
     * Update a category by ID.
     * Example: PUT /api/categories/5
     * Body: { "name": "Wines" }
     */
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,
                                                   @RequestBody Category category) {
        try {
            Category updated = categoryService.updateCategory(id, category);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a category by ID.
     * Example: DELETE /api/categories/5
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        boolean deleted = categoryService.deleteCategory(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}