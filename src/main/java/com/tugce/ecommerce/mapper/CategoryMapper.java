package com.tugce.ecommerce.mapper;

import com.tugce.ecommerce.dto.CategoryDTO;
import com.tugce.ecommerce.entity.Category;

public class CategoryMapper {
    public static CategoryDTO toDTO(Category category){
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
    public static Category toEntity(CategoryDTO categoryDTO){
        return Category.builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .build();
    }
}
