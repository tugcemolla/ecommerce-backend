package com.tugce.ecommerce.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.*;

import java.math.BigDecimal;

// Product Entity sınıfı, e-ticaret sistemindeki ürün bilgilerini temsil eder.
// Bu sınıf JPA tarafından "products" tablosuna dönüştürülür.
@Entity
@Table(name = "Products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ürün adı boş olamaz.")
    private String name;

    @NotBlank(message = "Ürün açıklaması boş olamaz.")
    private String description; // Ürün Açıklaması

    @NotNull(message = "Ürün fiyatı boş olamaz.")
    @Positive(message = "Ürün fiyatı 0'dan büyük olmalıdır.")
    private BigDecimal price; // Ürün Fiyatı

    @NotNull(message = "Stok miktarı boş olamaz.")
    @PositiveOrZero(message = "Stok miktarı negaitf olamaz.")
    private Integer stock;

    @NotBlank(message = "Ürün görsel bağlantısı boş olamaz.")
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
