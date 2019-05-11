package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Cart;
import com.example.easynotes.model.Orders;
import com.example.easynotes.model.OrderProducts;
import com.example.easynotes.repository.CartRepository;
import com.example.easynotes.repository.OrderProductsRepository;
import com.example.easynotes.repository.OrderRepository;
import com.example.easynotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/cart")
    public List<Cart> getAllCarts(){
        return cartRepository.findAll();
    }

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

    @GetMapping("/cart/{id}")
    public Cart getNoteById(@PathVariable(value = "id") Long cartID) {
        return cartRepository.findById(cartID)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartID));
    }

    @PutMapping("/cart/{id}")
    public Cart updateCart(@PathVariable(value = "id")Long cartID,
                                 @Valid @RequestBody Cart cartDetails){
        Cart cart = cartRepository.findById(cartID)
                .orElseThrow(()-> new ResourceNotFoundException("Cart", "id", cartID));

        cart.setProductID(cartDetails.getProductID());
        cart.setCartPrice(cartDetails.getCartPrice());
        cart.setCartQuantity(cartDetails.getCartQuantity());

        Cart updatedCart = cartRepository.save(cart);
        return updatedCart;
    }
    

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable(value = "id") Long cartID) {
        Cart cart = cartRepository.findById(cartID)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartID));

        cartRepository.delete(cart);

        return ResponseEntity.ok().build();
    }


}
