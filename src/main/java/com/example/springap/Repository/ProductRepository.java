package com.example.springap.Repository;

import com.example.springap.Models.Product;
import com.example.springap.Repository.Projections.ProductProjections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product save(Product product);

    void delete(Product product);

    Product findByTitle(String title);

    Product findByDescription(String description);

    //Get all Data
    @Query("select p from Product p")
    List<Product> getAll();

    //Updata Data
    @Modifying
    @Query(value = "UPDATE product p JOIN category c ON p.category_id = c.id " +
            "SET p.title = :title, p.description = :description, p.price = :price, c.title = :categoryTitle " +
            "WHERE p.id = :id", nativeQuery = true)
    int updateProductAndCategory(@Param("id") Long id, @Param("title") String title, @Param("description") String description, @Param("price") Double price, @Param("categoryTitle") String categoryTitle);



    //implement HQL
    @Query("select p from Product p where p.category.id = :categoryId")
    List<Product> getProductsByCategory(@Param("categoryId") Long categoryId);

    //implement Native query
    @Query(value = "select * from Product p where p.category_id = :categoryId", nativeQuery = true)
    List<Product> getProductsByCategoryByNativeQuery(@Param("categoryId") Long categoryId);

    // Using Projections
    @Query("select p.title as title, p.id as id from Product p where p.category.id = :categoryId")
    List<ProductProjections> getProductsByCategoryUsingProjections(@Param("categoryId") Long categoryId);
}
