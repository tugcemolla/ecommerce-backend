package com.tugce.ecommerce.dto;

import  jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDTO {
    @NotNull(message = "Order id boş olamaz.")
    private Long orderId;
}
