package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Product;
import com.example.easynotes.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @PostMapping("/products")
    public Product createProduct(@Valid @RequestBody Product product){
        return productRepository.save(product);
    }

    @GetMapping("/products/{id}")
    public Product getNoteById(@PathVariable(value = "id") Long productID) {
        return productRepository.findById(productID)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productID));
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable(value = "id")Long productID,
                                 @Valid @RequestBody Product productDetails){
        Product product = productRepository.findById(productID)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "id", productID));

        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());

        Product updatedProduct = productRepository.save(product);
        return updatedProduct;
    }
    

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long productID) {
        Product note = productRepository.findById(productID)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productID));

        productRepository.delete(note);

        return ResponseEntity.ok().build();
    }


}
