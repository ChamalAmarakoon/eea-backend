package com.example.easynotes.repository;

import com.example.easynotes.model.OrderProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductsRepository extends JpaRepository<OrderProducts,Long> {
    public OrderProducts findByProductId(long productId);

    public List<OrderProducts> findByOrderId(long orderId);

    public List<OrderProducts> findByOrderIdAndProductId(long orderId, long productId);
}

