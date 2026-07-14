package com.tugce.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor

public class UserRequestDTO {
    @NotBlank(message = "Ad boş olamaz.")
    private String firstName;

    @NotBlank(message = "Soyad boş olamaz.")
    private String lastName;

    @Email(message = "Geçerli bir E-posta giriniz.")
    @NotBlank(message = "E-posta boş bırakılamaz.")
    private String email;

    @NotBlank(message = "Şifre boş bırakılamaz.")
    private String password;

    @NotBlank(message = "Telefon numarası boş bırakılamaz.")
    private String phoneNumber;

    @NotBlank(message = "Adres boş bırakılamaz.")
    private String address;


}
