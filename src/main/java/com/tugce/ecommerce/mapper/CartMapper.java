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

    public CartItemResponseDTO toResponseDTO(CartItem cartItem) {

        boolean isGift = Boolean.TRUE.equals(cartItem.getGift());

        BigDecimal totalPrice = null;

        if (!isGift && cartItem.getProduct() != null) {
            totalPrice = cartItem.getProduct()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        }

        return CartItemResponseDTO.builder()
                .productId(
                        isGift || cartItem.getProduct() == null
                                ? null
                                : cartItem.getProduct().getId()
                )
                .productName(
                        isGift || cartItem.getProduct() == null
                                ? null
                                : cartItem.getProduct().getName()
                )
                .giftId(
                        isGift && cartItem.getGiftProduct() != null
                                ? cartItem.getGiftProduct().getId()
                                : null
                )
                .giftName(
                        isGift && cartItem.getGiftProduct() != null
                                ? cartItem.getGiftProduct().getName()
                                : null
                )
                .price(
                        isGift || cartItem.getProduct() == null
                                ? null
                                : cartItem.getProduct().getPrice()
                )
                .quantity(cartItem.getQuantity())
                .totalPrice(totalPrice)
                .gift(isGift)
                .message(
                        isGift
                                ? "20.000 TL üzeri alışveriş hediyesi"
                                : null
                )
                .build();
    }

    public CartResponseDTO toCartResponseDTO(Cart cart) {

        List<CartItemResponseDTO> itemDTOs = cart.getItems()
                .stream()
                .map(this::toResponseDTO)
                .toList();

        BigDecimal grandTotal = itemDTOs.stream()
                .filter(item -> !Boolean.TRUE.equals(item.getGift()))
                .map(CartItemResponseDTO::getTotalPrice)
                .filter(totalPrice -> totalPrice != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponseDTO.builder()
                .items(itemDTOs)
                .grandTotal(grandTotal)
                .build();
    }
}