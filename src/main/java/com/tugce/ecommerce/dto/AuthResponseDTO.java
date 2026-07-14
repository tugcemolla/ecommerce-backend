package com.tugce.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AuthResponseDTO {
    private String token; // JWT değeri
    private String tokenType;
    private Long userId;
    private String email;
    private String role;
}
