package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.*;
import com.example.easynotes.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderProductsRepository orderProductsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository prodRepository;

//    @GetMapping("/cart")
//    public List<Cart> getAllCarts(){
//        return cartRepository.findAll();
//    }

    @PostMapping("/cart")
    public OrderProducts createCart(@Valid @RequestBody OrderProducts newOrderProd){
        long userId = 2;
        Orders order = orderRepository.findByUserIdAndOrderStatus(2, "cart");
        if(order==null){
            order = new Orders();
            order.setOrderStatus("cart");
            order.setUserId(userId);
            order = orderRepository.save(order);
        }

        OrderProducts orderProd = orderProductsRepository.findByProductId(newOrderProd.getProductId());
        if(orderProd==null) {
            newOrderProd.setOrderId(order.getId());
            orderProd = newOrderProd;
        }else{
            orderProd.setQuantity(orderProd.getQuantity()+newOrderProd.getQuantity());
        }
        orderProd = orderProductsRepository.save(orderProd);
        return orderProd;
    }

    @GetMapping("/cart")
    public List<OrderProductDTO> getCart() {
//        return cartRepository.findById(cartID)
//                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartID));
        long userId = 2;
        Orders order = orderRepository.findByUserIdAndOrderStatus(2, "cart");
        List<OrderProductDTO> orderProdDTOList = new ArrayList<>();
        if(order!=null){

            List<OrderProducts> orderProdList = orderProductsRepository.findByOrderId(order.getId());
            for(OrderProducts orderProd : orderProdList){
                Optional<Product> product = prodRepository.findById(orderProd.getProductId());
                if(product.isPresent()){
                    OrderProductDTO orderProdDTO = new OrderProductDTO();
                    orderProdDTO.setOrderProd(orderProd);
                    orderProdDTO.setProduct(product.get());
                    orderProdDTOList.add(orderProdDTO);
                }
            }
        }
        return orderProdDTOList;
    }

    @PutMapping("/cart/{id}")
    public OrderProducts updateCart(@PathVariable(value = "id")Long cartID,
                                 @Valid @RequestBody OrderProducts newOrderProduct){
         List<OrderProducts> orderProds = orderProductsRepository.
                 findByOrderIdAndProductId(cartID,newOrderProduct.getProductId());
//                .orElseThrow(()-> new ResourceNotFoundException("Cart", "id", cartID));
        System.out.println(cartID);
        System.out.println(newOrderProduct.getProductId());
        if(orderProds.size()>0){
            OrderProducts orderProdduct = orderProds.get(0);
            if(newOrderProduct.getQuantity()>0) {
                orderProdduct.setQuantity(newOrderProduct.getQuantity());
                System.out.println("Quantity : " + newOrderProduct.getQuantity());
                orderProductsRepository.save(orderProdduct);
            }
            return orderProdduct;
        }
        return null;
//        cart.setProductID(cartDetails.getProductID());
//        cart.setCartPrice(cartDetails.getCartPrice());
//        cart.setCartQuantity(cartDetails.getCartQuantity());
//
//        Cart updatedCart = cartRepository.save(cart);
//        return updatedCart;
    }

    @DeleteMapping("/cart/{cartId}/{productId}")
    public boolean deleteCartProduct(@PathVariable Long cartId
    ,@PathVariable Long productId) {
        List<OrderProducts> orderProds = orderProductsRepository.
                findByOrderIdAndProductId(cartId,productId);
        if(orderProds.size()>0){
            OrderProducts orderProduct = orderProds.get(0);
            orderProductsRepository.delete(orderProduct);
            return true;
        }
        return false;
    }

    @DeleteMapping("/cart/{id}")
    public boolean deleteCart(@PathVariable(value = "id") Long cartID) {
//        Cart cart = cartRepository.findById(cartID)
//                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartID));
        Optional<Orders> order = orderRepository.findById(cartID);
        if(order.isPresent()){
            List<OrderProducts> orderProds = orderProductsRepository.findByOrderId(cartID);
            for(OrderProducts orderProd : orderProds){
                orderProductsRepository.deleteById(orderProd.getId());
            }
            orderRepository.deleteById(cartID);
            return true;
        }
        return false;
//        cartRepository.delete(cart);
//
//        return ResponseEntity.ok().build();
    }


}
