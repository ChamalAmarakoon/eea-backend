package com.example.easynotes.controller;

import com.example.easynotes.model.*;
import com.example.easynotes.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
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
        if(orderProdList.size()==0) {
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
    public List<OrderProducts> getCart() {
        long userId = 2;
        Orders order = orderRepository.findByUserIdAndOrderStatus(2, "cart");
        //OrdersDTO orderDTO = ordersToDTO(order);
        List<OrderProducts> orderProdDTOList = new ArrayList<>();
        if(order!=null){
//            List<OrderProducts> orderProdList = orderProductsRepository.findByOrders(order);
//            for(OrderProducts orderProd : orderProdList){
//                Product product = orderProd.getProduct();
//                if(product!=null){
//                    ProductDTO productDTO = productToDTO(product);
//                    OrderProductDTO orderProductDTO = orderProductToDTO(orderProd);
//
//                    OrdersDTO ordersDTO = new OrdersDTO();
//                    ordersDTO.setId(order.getId());
//
//                    orderProductDTO.setProductDTO(productDTO);
//                    orderProductDTO.setOrdersDTO(ordersDTO);
//                    orderProdDTOList.add(orderProductDTO);
//                }
//            }
            return order.getOrderProducts();
        }
        return null;
    }

    @PutMapping("/cart")
    public ResponseEntity<Object> updateCart(
                                             @Valid @RequestBody OrderProducts newOrderProduct){
        Product product = prodRepository.findById(newOrderProduct.getProduct().getId()).get();
        Orders orders = orderRepository.findByOrderStatus("cart");
         List<OrderProducts> orderProds = orderProductsRepository.
                 findByOrdersAndProduct(orders,product);

       // System.out.println("cart id "+cartID);
       // System.err.println(orderProds.size());
        if(orderProds.size()>0 ){
            OrderProducts orderProduct = orderProds.get(0);
            //System.err.println(product.getQuantity());
            if(newOrderProduct.getQuantity()>0 && newOrderProduct.getQuantity()<=product.getQuantity()) {
                orderProduct.setQuantity(newOrderProduct.getQuantity());
                //System.out.println("Quantity : " + newOrderProduct.getQuantity());
                orderProduct = orderProductsRepository.save(orderProduct);
                //OrderProductDTO orderProductDTO = orderProductToDTO(orderProduct);
                return new ResponseEntity<>(orderProduct, HttpStatus.OK);
            }
           //return orderProduct;

        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        return null;
    }

    @PutMapping("/cart/{status}")
    public Orders updateCartStatus(@PathVariable(value = "status")String status){
        Orders order = orderRepository.findByOrderStatus("cart");
        if(order!=null){
            order.setOrderStatus(status);
            orderRepository.save(order);

            List<OrderProducts> orderProds = order.getOrderProducts();
            for(OrderProducts orderProd : orderProds){
                Product prod = orderProd.getProduct();
                prod.setQuantity(prod.getQuantity()-orderProd.getQuantity());
                prodRepository.save(prod);
            }
            return order;
        }
        return null;
    }

    @DeleteMapping("/cart/{productId}")
    public boolean deleteCartProduct(@PathVariable Long productId) {
        Product product = prodRepository.findById(productId).get();
        Orders order = orderRepository.findByOrderStatus("cart");
        List<OrderProducts> orderProds = orderProductsRepository.
                findByOrdersAndProduct(order,product);
        if(orderProds.size()>0){
            OrderProducts orderProduct = orderProds.get(0);
            orderProductsRepository.delete(orderProduct);
            return true;
        }
        return false;
    }

    @DeleteMapping("/deleteCart")
    public boolean deleteCart() {
        Orders order = orderRepository.findByOrderStatus("cart");
        System.err.println(order);
        if(order!=null){
            List<OrderProducts> orderProds = orderProductsRepository.findByOrders(order);
            for(OrderProducts orderProd : orderProds){
                orderProductsRepository.deleteById(orderProd.getId());
            }
            orderRepository.deleteById(order.getId());
            return true;
        }
        return false;
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
//
//    private OrderProductDTO orderProductToDTO(OrderProducts orderProduct){
//        OrderProductDTO orderProductDTO = new OrderProductDTO();
//        orderProductDTO.setId(orderProduct.getId());
//        orderProductDTO.setQuantity(orderProduct.getQuantity());
//        return orderProductDTO;
//    }

}
