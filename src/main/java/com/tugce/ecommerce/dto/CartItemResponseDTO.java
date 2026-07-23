package com.tugce.ecommerce.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponseDTO {

    private Long productId;
    private String productName;

    private Long giftId;
    private String giftName;

    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalPrice;

    private Boolean gift;
    private String message;
}