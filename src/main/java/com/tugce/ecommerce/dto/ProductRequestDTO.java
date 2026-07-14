package com.tugce.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class ProductRequestDTO {
    @NotBlank(message = "Ürün adı boş olamaz.")
    private String name;

    @NotBlank(message = "Ürün açıklaması boş olamaz.")
    private String description;

    @NotNull(message = "Ürün fiyatı boş olamaz.")
    @Positive(message = "Ürün fiyatı 0'dan büyük olmalıdır.")
    private BigDecimal price;

    @NotNull(message = "Stok miktarı boş olamaz.")
    @PositiveOrZero(message = "Stok miktarı negatif olamaz.")
    private Integer stock;

    @NotBlank(message = "Ürün görsel bağlantısı boş olamaz.")
    private String imageUrl;
    @NotNull(message = "Kategori seçilmelidir.")
    private Long categoryId;

    public ProductRequestDTO(){

    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public BigDecimal getPrice(){
        return price;
    }

    public void setPrice(BigDecimal price){
        this.price = price;
    }

    public Integer getStock(){
        return stock;
    }

    public void setStock(Integer stock){
        this.stock = stock;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public Long getCategoryId(){
        return categoryId;
    }
    public void setCategoryId(Long categoryId){
        this.categoryId = categoryId;
    }



}
