package org.example.barmangementsystem.services;


import org.example.barmangementsystem.entity.Product;
import org.example.barmangementsystem.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // --- Create / Save ---
    public String saveProduct(Product product) {
        if (productRepository.existsByNameAndCategory(product.getName(), product.getCategory())) {
            return "Product already exists";
        } else {
            productRepository.save(product);
            return "Product saved";
        }
    }


    // --- Read ---
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public List<Product> getOutOfStockProducts() {
        return productRepository.findByStockQuantityLessThanEqual(0);
    }
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }



    // --- Update ---
    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedProduct.getName());
                    existing.setPrice(updatedProduct.getPrice());
                    existing.setCostPrice(updatedProduct.getCostPrice());
                    existing.setStockQuantity(updatedProduct.getStockQuantity());
                    existing.setCategory(updatedProduct.getCategory());
                    return productRepository.save(existing);
                })
                .orElseThrow(() ->
                        new IllegalArgumentException("Product with ID " + id + " not found"));
    }

    // --- Delete ---
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
