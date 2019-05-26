package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.*;
import com.example.easynotes.repository.*;
import org.hibernate.criterion.Order;
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
    OrderRepository orderRepository;

    @Autowired
    OrderProductsRepository orderProductsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository prodRepository;

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

        Product product = prodRepository.findById(newOrderProd.getProduct().getId()).get();
        List<OrderProducts> orderProdList = orderProductsRepository.findByOrdersAndProduct(order,product);
        OrderProducts orderProd=null;
        if(orderProd==null) {

            newOrderProd.setOrders(orderRepository.findById(order.getId()).get());
            orderProd = newOrderProd;
        }else{
            orderProd = orderProdList.get(0);
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

            List<OrderProducts> orderProdList = orderProductsRepository.findByOrders(order);
            for(OrderProducts orderProd : orderProdList){
                Optional<Product> product = prodRepository.findById(orderProd.getProduct().getId());
                if(product.isPresent()){
                    OrderProductDTO orderProdDTO = new OrderProductDTO();
//                    orderProdDTO.setOrderProd(orderProd);
//                    orderProdDTO.setProduct(product.get());
                    orderProdDTOList.add(orderProdDTO);
                }
            }
        }
        return orderProdDTOList;
    }

    @PutMapping("/cart/{id}")
    public OrderProducts updateCart(@PathVariable(value = "id")Long cartID,
                                 @Valid @RequestBody OrderProducts newOrderProduct){
        Product product = prodRepository.findById(newOrderProduct.getProduct().getId()).get();
        Orders orders = orderRepository.findById(cartID).get();
         List<OrderProducts> orderProds = orderProductsRepository.
                 findByOrdersAndProduct(orders,product);
//                .orElseThrow(()-> new ResourceNotFoundException("Cart", "id", cartID));
        System.out.println(cartID);
        //System.out.println(newOrderProduct.getProductId());
        if(orderProds.size()>0){
            OrderProducts orderProduct = orderProds.get(0);
            if(newOrderProduct.getQuantity()>0) {
                orderProduct.setQuantity(newOrderProduct.getQuantity());
                System.out.println("Quantity : " + newOrderProduct.getQuantity());
                orderProductsRepository.save(orderProduct);
            }
            return orderProduct;
        }
        return null;
//        cart.setProductID(cartDetails.getProductID());
//        cart.setCartPrice(cartDetails.getCartPrice());
//        cart.setCartQuantity(cartDetails.getCartQuantity());
//
//        Cart updatedCart = cartRepository.save(cart);
//        return updatedCart;
    }

    @PutMapping("/cart/{id}/{status}")
    public Orders updateCartStatus(@PathVariable(value = "id")Long cartID, @PathVariable(value = "status")String status){
        Optional<Orders> orders = orderRepository.findById(cartID);
        if(orders.isPresent()){
            Orders order = orders.get();
            order.setOrderStatus(status);
            orderRepository.save(order);
            return order;
        }
//        List<OrderProducts> orderProds = orderProductsRepository.
//                findByOrderIdAndProductId(cartID,newOrderProduct.getProductId());
////                .orElseThrow(()-> new ResourceNotFoundException("Cart", "id", cartID));
//        System.out.println(cartID);
//        System.out.println(newOrderProduct.getProductId());
//        if(orderProds.size()>0){
//            OrderProducts orderProduct = orderProds.get(0);
//            if(newOrderProduct.getQuantity()>0) {
//                orderProduct.setQuantity(newOrderProduct.getQuantity());
//                System.out.println("Quantity : " + newOrderProduct.getQuantity());
//                orderProductsRepository.save(orderProduct);
//            }
//            return orderProduct;
//        }
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
        Product product = prodRepository.findById(productId).get();
        Orders order = orderRepository.findById(cartId).get();
        List<OrderProducts> orderProds = orderProductsRepository.
                findByOrdersAndProduct(order,product);
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
            List<OrderProducts> orderProds = orderProductsRepository.findByOrders(order.get());
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
