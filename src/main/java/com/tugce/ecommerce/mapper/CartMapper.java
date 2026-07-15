package com.tugce.ecommerce.mapper;

import com.tugce.ecommerce.dto.CartItemResponseDTO;
import com.tugce.ecommerce.entity.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {
    public CartItemResponseDTO toResponseDTO(CartItem cartItem){
        return CartItemResponseDTO.builder()
                .productId(cartItem.getProduct().getId())
                .productName(cartItem.getProduct().getName())
                .price(cartItem.getProduct().getPrice())
                .quantity(cartItem.getQuantity())
                .totalprice(
                        cartItem.getProduct()
                                .getPrice()
                                .multiply(
                                        java.math.BigDecimal.valueOf(
                                                cartItem.getQuantity()
                                        )//toplam fiyat hesaplama
                                )
                )
                .build();
    }
}
