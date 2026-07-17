package com.tugce.ecommerce.service.impl;

import com.tugce.ecommerce.dto.CategoryDTO;
import com.tugce.ecommerce.entity.Category;
import com.tugce.ecommerce.exception.ResourceNotFoundException;
import com.tugce.ecommerce.mapper.CategoryMapper;
import com.tugce.ecommerce.repository.CategoryRepository;
import com.tugce.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO){
        Category category = CategoryMapper.toEntity((categoryDTO));
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.toDTO((savedCategory));
    }

    @Override
    public CategoryDTO getCategoryById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException("Kategori bulunamadı."));

       return CategoryMapper.toDTO(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper:: toDTO)
                .toList();
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO){
       Category category = categoryRepository.findById(id)
               .orElseThrow(() ->  new ResourceNotFoundException("Kategori bulunamadı."));
       category.setName(categoryDTO.getName());
       Category updateCategory = categoryRepository.save(category);
       return CategoryMapper.toDTO(updateCategory);
    }

    @Override
    public CategoryDTO deleteCategory(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException("Kategori bulunamadı."));
        categoryRepository.delete(category);

        return null;
    }
}
