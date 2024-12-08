package com.example.springap.Packages;

import com.example.springap.Models.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @RequestMapping(value="/create", method= RequestMethod.POST)
    public void createProduct(Product product) {

    }

    @RequestMapping(value="/get", method = RequestMethod.GET)
    public Product getProduct(Long id) {
        return null;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public void updateProduct(Product product) {

    }

    public void deleteProduct(Long id) {

    }
}
