package com.tugce.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCampaignRequestDTO {

    @NotNull(message = "Kategori ID boş olamaz.")
    private Long categoryId;

    @NotNull(message = "Hediye ID boş olamaz.")
    private Long giftId;

    @NotNull(message = "Minimum tutar boş olamaz.")
    @Positive(message = "Minimum tutar sıfırdan büyük olmalıdır.")
    private BigDecimal minimumAmount;

    @NotNull(message = "Kampanya durumu boş olamaz.")
    private Boolean active;
}