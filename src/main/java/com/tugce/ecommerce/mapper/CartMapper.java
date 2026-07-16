package com.tugce.ecommerce.mapper;

import com.tugce.ecommerce.dto.CartItemResponseDTO;
import com.tugce.ecommerce.dto.CartResponseDTO;
import com.tugce.ecommerce.entity.Cart;
import com.tugce.ecommerce.entity.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

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
    public CartResponseDTO tocartResponseDTO(Cart cart){
        List<CartItemResponseDTO> itemDTOs = cart.getItems()
                .stream()
                .map(this::toResponseDTO)
                .toList();
        BigDecimal grandTotal = itemDTOs.stream()
                .map(CartItemResponseDTO::getTotalprice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return CartResponseDTO.builder()
                .items(itemDTOs)
                .grandTotal(grandTotal)
                .build();
    }
}
