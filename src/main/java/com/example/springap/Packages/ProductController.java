package com.example.springap.Packages;

import com.example.springap.Models.Product;
import com.example.springap.Services.ProductService;
import com.example.springap.dto.ErrorDto;
import com.example.springap.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value="/products", method= RequestMethod.POST)
    public Product createProduct(@RequestBody Product product) {
        Product p = productService.createProduct(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl(),
                product.getCategory().getTitle()
        );

        return p;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        System.out.println("Starting API");
        Product p = productService.getSingleProduct(id);
        System.out.println("Ending API");

        ResponseEntity<Product> response = new ResponseEntity<>(
                p, HttpStatus.OK
        );

        return response;
    }

    @DeleteMapping("/products/delete/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        System.out.println("Item " + id + " deleted");
    }

    @PutMapping("/product/update/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        Product p = productService.updateProduct(
                id,  // Pass the id to the service
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory().getTitle()
        );
        return p;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handlingProductNotFoundException(Exception e){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(e.getMessage());

        return new ResponseEntity<>(
                errorDto, HttpStatus.NOT_FOUND
        );
    }
}
