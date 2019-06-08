package com.example.easynotes.repository;

import com.example.easynotes.model.OrderProducts;
import com.example.easynotes.model.Orders;
import com.example.easynotes.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductsRepository extends JpaRepository<OrderProducts,Long> {
    public List<OrderProducts> findByProduct(Product product);

    public List<OrderProducts> findByOrders(Orders orders);

    public List<OrderProducts> findByOrdersAndProduct(Orders order, Product product);
}

