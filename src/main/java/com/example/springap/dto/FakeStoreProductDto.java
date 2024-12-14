package com.example.springap.dto;

import com.example.springap.Models.Category;
import com.example.springap.Models.Product;

public class FakeStoreProductDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String imageUrl; // Renamed for consistency
    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Product getProduct() {
        Product product = new Product();

        product.setId(id);
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);

        // Map category string to Category object
        Category cat = new Category();
        cat.setId(0L); // Set default or appropriate ID
        cat.setTitle(category); // Use category string as the title
        product.setCategory(cat);

        return product;
    }

    @Override
    public String toString() {
        return "FakeStoreProductDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
