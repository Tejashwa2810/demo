package com.example.springap.Services;

import com.example.springap.Models.Category;
import com.example.springap.Models.Product;
import com.example.springap.Repository.CategoryRepository;
import com.example.springap.Repository.ProductRepository;
import com.example.springap.exceptions.ProductNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService{

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()){
            return product.get();
        }

        throw new ProductNotFoundException("Product not found");
    }


    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String fieldName) {
          Page<Product> products = productRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(fieldName).ascending()));
          return products;
    }

    @Override
    public Product createProduct(Long id, String title, String description, Double price, String imageUrl, String categoryTitle) {
        Product product = new Product();
        Optional<Category> currCategory = categoryRepository.findByTitle(categoryTitle);
        if(currCategory.isEmpty()){
            Category newCategory = new Category();
            newCategory.setTitle(categoryTitle);
            Category newRow = categoryRepository.save(newCategory);
            product.setCategory(newRow);
        }else{
            Category currentCat = currCategory.get();
            product.setCategory(currentCat);
        }

        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);
        product.setTitle(title);
        Product savedPRoduct = productRepository.save(product);
        return savedPRoduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public void deleteProduct(Long id) {
         productRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Product updateProduct(Long id, String title, String description, Double price, String category) {
        int rowsUpdated = productRepository.updateProductAndCategory(id, title, description, price, category);
        if (rowsUpdated == 0) {
            throw new RuntimeException("No product found with the given ID.");
        }
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found after update"));
    }

}
