package com.tugce.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ad boş olamaz.")
    private String firstName;

    @NotBlank(message = "Soyad boş olamaz.")
    private String lastName;

    @Email(message = "Geçerli bir e-posta giriniz.")
    @NotBlank(message = "E-posta boş olamaz.")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Şifre boş olamaz.")
    private String password;

    @NotBlank(message = "Telefon numarası boş olamaz.")
    private String phoneNumber;

    @NotBlank(message = "Adres boş olamaz.")
    private String address;

    @NotBlank(message = "Rol boş olamaz.")
    private String role;
}
