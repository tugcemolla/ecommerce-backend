package com.tugce.ecommerce.repository;

import com.tugce.ecommerce.entity.Order;
import com.tugce.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByCreatedAtDesc(User user);
    // Kullanıcının siparişlerini en yeniden en eskiye doğru getirir.
}
