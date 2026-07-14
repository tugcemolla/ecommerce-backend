package com.tugce.ecommerce.mapper;

import com.tugce.ecommerce.dto.ProductRequestDTO;
import  com.tugce.ecommerce.dto.ProductResponseDTO;
import com.tugce.ecommerce.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toEntity(ProductRequestDTO requestDTO){
        Product product = new Product();

        product.setName(requestDTO.getName());
        product.setDescription(requestDTO.getDescription());
        product.setPrice(requestDTO.getPrice());
        product.setStock(requestDTO.getStock());
        product.setImageUrl(requestDTO.getImageUrl());

        return product;
    }

    public ProductResponseDTO toResponseDTO(Product product){
    ProductResponseDTO responseDTO = new ProductResponseDTO();

    responseDTO.setId(product.getId());
    responseDTO.setName(product.getName());
    responseDTO.setDescription(product.getDescription());
    responseDTO.setPrice(product.getPrice());
    responseDTO.setStock(product.getStock());
    responseDTO.setImageUrl(product.getImageUrl());
    responseDTO.setCategoryId((product.getCategory().getId()));
    responseDTO.setCategoryName(product.getCategory().getName());

    return responseDTO;
    }
}
