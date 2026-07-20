package com.tugce.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftRequestDTO {
    @NotBlank(message = "Hediye adı boş olamaz.")
    private String name;

    private String description;

    @NotNull(message = "Hediye stoğu boş olamaz.")
    @PositiveOrZero(message = "Hediye stoğu negatif olamaz.")
    private Integer stock;

    private String imageUrl;
}
