package com.tugce.ecommerce.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCampaignResponseDTO {

    private Long id;

    private Long categoryId;
    private String categoryName;

    private Long giftId;
    private String giftName;

    private BigDecimal minimumAmount;
    private Boolean active;
}