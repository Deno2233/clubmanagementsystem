package org.example.barmangementsystem.services;


import org.example.barmangementsystem.entity.Category;
import org.example.barmangementsystem.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // Constructor injection is recommended for immutability & easier testing
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Save or update a category.
     * If the Category has an ID, JPA will perform an update; otherwise, an insert.
     */
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Retrieve all categories.
     */
    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    /**
     * Retrieve a single category by its ID.
     */
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }


    /**
     * Update a category's details.
     * Throws IllegalArgumentException if the ID does not exist.
     */
    public Category updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedCategory.getName());
                    return categoryRepository.save(existing);
                })
                .orElseThrow(() ->
                        new IllegalArgumentException("Category with ID " + id + " not found"));
    }

    /**
     * Delete a category by its ID.
     * Returns true if deleted, false if not found.
     */
    public boolean deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}