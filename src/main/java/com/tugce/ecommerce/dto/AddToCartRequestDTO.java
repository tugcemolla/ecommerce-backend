package com.tugce.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddToCartRequestDTO {
    @NotNull(message = "Ürün seçilmelidir.")
    private Long productId;

    @NotNull(message = "Ürün adedi boş olamaz.")
    @Positive(message = "Ürün adedi 0'dan büyük olmalıdır.")
    private Integer quantity;
}
