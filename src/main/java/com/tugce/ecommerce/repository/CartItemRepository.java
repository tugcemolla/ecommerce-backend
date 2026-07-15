package com.tugce.ecommerce.repository;

import com.tugce.ecommerce.entity.Cart;
import com.tugce.ecommerce.entity.CartItem;
import com.tugce.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(
            Cart cart,
            Product product
            );
}
