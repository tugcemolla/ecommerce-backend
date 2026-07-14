package com.tugce.ecommerce.service;
import com.tugce.ecommerce.dto.ProductRequestDTO;
import com.tugce.ecommerce.dto.ProductResponseDTO;

import java.util.List;


public interface ProductService {
    ProductResponseDTO saveProduct(ProductRequestDTO produrequestDTO);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO getProductById(Long id);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO);
    void deleteProduct(Long id);
}
