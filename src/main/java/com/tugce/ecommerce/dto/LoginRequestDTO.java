package com.tugce.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LoginRequestDTO {
    @Email(message = "Geçerli bir e-posta giriniz.")
    @NotBlank(message = "E-mail alanı boş olamaz.")
    private String email;

    @NotBlank(message = "Şifre alanı boş olamaz.")
    private String password;
}
