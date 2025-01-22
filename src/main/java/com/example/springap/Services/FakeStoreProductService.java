package com.example.springap.Services;

import com.example.springap.Models.Product;
import com.example.springap.dto.FakeStoreProductDto;
import com.example.springap.exceptions.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private final RestTemplate restTemplate;  // Injects RestTemplate in this Service
    private final RedisTemplate<String, Object> redisTemplate;

    // Used this just to be extra sure
    public FakeStoreProductService(RestTemplate restTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }


//    @Override
//    public Product getSingleProduct(Long id) throws ProductNotFoundException {
//        System.out.println("Starting API");
//        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeStoreProductDto.class);
//
//        if(fakeStoreProductDto == null){
//            throw new ProductNotFoundException("Product not found with id " + id);
//        }
//
//        //assert fakeStoreProductDto != null;
//
//        System.out.println(fakeStoreProductDto.toString());
//        return fakeStoreProductDto.getProduct();  /* Tells that "fakeStoreProductDto" maps external properties
//                                                     into our class defined properties */
//    }




    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        Product redisProduct = (Product) redisTemplate.opsForHash().get("PRODUCTS", "PRODUCTS_"+id);

        if(redisProduct != null){
            return redisProduct;
        }
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeStoreProductDto.class);
        if(fakeStoreProductDto == null){
            throw new ProductNotFoundException("Product not found with id " + id);
        }
        redisTemplate.opsForHash().put("PRODUCTS", "PRODUCTS_"+id, fakeStoreProductDto.getProduct());
        return fakeStoreProductDto.getProduct();
    }

    @Override
    public List<Product> getAllProducts() {
        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.exchange("https://fakestoreapi.com/products/", HttpMethod.GET, null, FakeStoreProductDto[].class);
        FakeStoreProductDto[] productDto = response.getBody();
        assert productDto != null;

        return Arrays.stream(productDto).map(FakeStoreProductDto::getProduct).toList();
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
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String fieldName) {
        return null;
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
