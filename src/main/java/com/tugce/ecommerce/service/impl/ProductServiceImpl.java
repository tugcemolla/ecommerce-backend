package com.tugce.ecommerce.service.impl;

import com.tugce.ecommerce.dto.ProductRequestDTO;
import com.tugce.ecommerce.dto.ProductResponseDTO;
import com.tugce.ecommerce.entity.Product;
import com.tugce.ecommerce.exception.ProductNotFoundException;
import com.tugce.ecommerce.mapper.ProductMapper;
import com.tugce.ecommerce.repository.ProductRepository;
import com.tugce.ecommerce.service.ProductService;
import org.springframework.stereotype.Service;
import com.tugce.ecommerce.entity.Category;
import com.tugce.ecommerce.repository.CategoryRepository;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductResponseDTO saveProduct(ProductRequestDTO requestDTO){
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı."));
        Product product = productMapper.toEntity(requestDTO);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts(){

        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return productMapper.toResponseDTO(product);
    }

    @Override
    public ProductResponseDTO updateProduct(
            Long id,
            ProductRequestDTO requestDTO){
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı."));
        existingProduct.setName(requestDTO.getName());
        existingProduct.setDescription(requestDTO.getDescription());
        existingProduct.setPrice(requestDTO.getPrice());
        existingProduct.setStock(requestDTO.getStock());
        existingProduct.setImageUrl(requestDTO.getImageUrl());
        existingProduct.setCategory(category);

        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toResponseDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }
}


