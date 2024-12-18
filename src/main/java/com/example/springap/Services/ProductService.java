package com.example.springap.Services;

import com.example.springap.Models.Product;
import com.example.springap.exceptions.ProductNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long id) throws ProductNotFoundException;
    List<Product> getAllProducts();
    Product createProduct(Long id, String title, String description, Double price, String imageUrl, String category);
    void deleteProduct(Long id);
    Product updateProduct(Long id, String title, String description, Double price, String category);
}
