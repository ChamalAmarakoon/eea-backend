package com.example.easynotes.repository;

import com.example.easynotes.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
        public Orders findByUserIdAndOrderStatus(long userId, String OrderStatus);

        public Orders findByOrderStatus(String orderStatus);
}
