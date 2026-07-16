package com.tugce.ecommerce.service;

import com.tugce.ecommerce.dto.AddToCartRequestDTO;
import com.tugce.ecommerce.dto.CartResponseDTO;

public interface CartService {
    CartResponseDTO addToCart(
            String email,
            AddToCartRequestDTO requestDTO
    );
    CartResponseDTO getCart(String email);
    CartResponseDTO updateQuantity(
            String email,
            Long productId,
            Integer quantity
    );
    CartResponseDTO removeFromCart(
            String email,
            Long productId
    );
    void clearCart(String email);
}
