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
        boolean isGift = Boolean.TRUE.equals(cartItem.getGift());
        BigDecimal totalPrice = isGift
                ? null
                : cartItem.getProduct()
                  .getPrice()
                  .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

        return CartItemResponseDTO.builder()
                .productId(cartItem.getProduct().getId())
                .productName(cartItem.getProduct().getName())
                .price(isGift ? null : cartItem.getProduct().getPrice())
                .quantity(cartItem.getQuantity())
                .totalprice(totalPrice)
                .gift(isGift)
                .message(
                        isGift
                        ? "20.000 TL üzeri alışveriş hediyesi"
                                : null
                )
                .build();
    }
    public CartResponseDTO tocartResponseDTO(Cart cart){
        List<CartItemResponseDTO> itemDTOs = cart.getItems()
                .stream()
                .map(this::toResponseDTO)
                .toList();
        BigDecimal grandTotal = itemDTOs.stream()
                .filter(item -> !Boolean.TRUE.equals(item.getGift()))
                .map(CartItemResponseDTO::getTotalprice)
                .filter(totalPrice -> totalPrice != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return CartResponseDTO.builder()
                .items(itemDTOs)
                .grandTotal(grandTotal)
                .build();
    }
}
