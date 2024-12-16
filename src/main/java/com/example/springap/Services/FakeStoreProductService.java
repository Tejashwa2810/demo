package com.example.springap.Services;

import com.example.springap.Models.Product;
import com.example.springap.dto.FakeStoreProductDto;
import com.example.springap.exceptions.ProductNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {

    private final RestTemplate restTemplate;  // Injects RestTemplate in this Service

    // Used this just to be extra sure
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        System.out.println("Starting API");
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeStoreProductDto.class);

        if(fakeStoreProductDto == null){
            throw new ProductNotFoundException("Product not found with id " + id);
        }

        //assert fakeStoreProductDto != null;

        System.out.println(fakeStoreProductDto.toString());
        return fakeStoreProductDto.getProduct();  /* Tells that "fakeStoreProductDto" maps external properties
                                                     into our class defined properties */
    }

    @Override
    public List<Product> getAllProducts() {
        System.out.println("Fetching all products");
        return List.of();
    }

    @Override
    public void deleteProduct(Long id){
        restTemplate.delete("https://fakestoreapi.com/products/" + id);
    }

    @Override
    public Product updateProduct(Long id, String title, String description, Double price, String category) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(title);
        fakeStoreProductDto.setDescription(description);
        fakeStoreProductDto.setPrice(price);
        fakeStoreProductDto.setCategory(category);

        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id,  // Use the id in the URL
                HttpMethod.PUT,
                new HttpEntity<>(fakeStoreProductDto),
                FakeStoreProductDto.class
        );

        FakeStoreProductDto updatedProductDto = response.getBody();
        assert updatedProductDto != null;
        return updatedProductDto.getProduct(); /* When we update a product (or do anything with the API),
                                                  the API gives us back a response. This response is in the
                                                  form of a FakeStoreProductDto, not a Product.
                                                  So, after the API gives us back the updated data,
                                                  we need to convert the FakeStoreProductDto into a
                                                  Product using the getProduct() method. */
    }


    @Override
    public Product createProduct(Long id, String title, String description, Double price, String imageUrl, String category) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(id);
        fakeStoreProductDto.setTitle(title);
        fakeStoreProductDto.setDescription(description);
        fakeStoreProductDto.setPrice(price);
        fakeStoreProductDto.setCategory(category);

        FakeStoreProductDto response = restTemplate.postForObject("https://fakestoreapi.com/products/", fakeStoreProductDto, FakeStoreProductDto.class);
        assert response != null;

        return response.getProduct();
    }


}
