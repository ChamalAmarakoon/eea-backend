package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.*;
import com.example.easynotes.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    public List<ProductDTO> getAllProducts(){
        List<ProductDTO> productDTOList = new ArrayList<>();
        for(Product product : productRepository.findAll()){
            ProductDTO productDTO = productToDTO(product);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    private ProductDTO productToDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCompany(product.getCompany());
        productDTO.setDescription(product.getDescription());
        productDTO.setId(product.getId());
        productDTO.setImage(product.getImage());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setTitle(product.getTitle());

//        List<OrderProducts> orderProductsList = product.getOrderProducts();
//        //System.out.println(orderProductsList.size());
//        List<OrderProductDTO> orderProductDTOList = new ArrayList<>();
//        for(OrderProducts orderProducts : orderProductsList){
//            OrderProductDTO orderProductDTO = new OrderProductDTO();
//            orderProductDTO.setId(orderProducts.getId());
//            orderProductDTO.setQuantity(orderProducts.getQuantity());
//
//            Orders orders = orderProducts.getOrders();
//            OrdersDTO ordersDTO = new OrdersDTO();
//            ordersDTO.setId(orders.getId());
//            ordersDTO.setOrderStatus(orders.getOrderStatus());
//            ordersDTO.setUserId(orders.getUserId());
//
//            orderProductDTO.setOrdersDTO(ordersDTO);
//            orderProductDTOList.add(orderProductDTO);
//        }
//        productDTO.setOrderProductDTOList(orderProductDTOList);
        return productDTO;
    }

    @PostMapping("/products")
    public Product createProduct(@Valid @RequestBody Product product){
        return productRepository.save(product);
    }

    @GetMapping("/products/{id}")
    public Product getNoteById(@PathVariable(value = "id") Long productID) {
//        return productRepository.findById(productID)
    //            .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productID));
        return productRepository.findById(productID).get();
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
