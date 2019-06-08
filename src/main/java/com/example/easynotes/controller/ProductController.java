package com.example.easynotes.controller;

import com.example.easynotes.model.ProductDTO;
import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.*;
import com.example.easynotes.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    

    @GetMapping("/products")
    public List<Product> getAllProducts(){
//        List<Product> productDTOList = new ArrayList<>();
//        for(Product product : productRepository.findAll()){
//            ProductDTO productDTO = productToDTO(product);
//            productDTOList.add(productDTO);
//        }
        System.err.println(productRepository.findAll());
        return productRepository.findAll();
    }

//    private ProductDTO productToDTO(Product product){
//        ProductDTO productDTO = new ProductDTO();
//        productDTO.setCompany(product.getCompany());
//        productDTO.setDescription(product.getDescription());
//        productDTO.setId(product.getId());
//        productDTO.setImage(product.getImage());
//        productDTO.setPrice(product.getPrice());
//        productDTO.setQuantity(product.getQuantity());
//        productDTO.setTitle(product.getTitle());
//        return productDTO;
//    }

    @PostMapping("/products")
    public Product createProduct(@Valid @RequestBody ProductDTO prodDTO){
        Product product = new Product();
        product.setTitle(prodDTO.getTitle());
        product.setDescription(prodDTO.getDescription());
        product.setQuantity(prodDTO.getQuantity());
        product.setPrice(prodDTO.getPrice());
        System.err.println(prodDTO.getImage());
        byte[] image = Base64.getDecoder().decode(prodDTO.getImage());
        product.setImage(image);
        product.setCompany(prodDTO.getCompany());
        return productRepository.save(product);
       // return product;
    }

    @GetMapping("/products/{id}")
    public Product getNoteById(@PathVariable(value = "id") Long productID) {
//        return productRepository.findById(productID)
    //            .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productID));

        return productRepository.findById(productID).get();
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable(value = "id")Long productID,
                                 @Valid @RequestBody ProductDTO productDetails){
        Product product = productRepository.findById(productID)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "id", productID));

        product.setTitle(productDetails.getTitle());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        product.setCompany(productDetails.getCompany());
        byte[] image = Base64.getDecoder().decode(productDetails.getImage());
        product.setImage(image);

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
