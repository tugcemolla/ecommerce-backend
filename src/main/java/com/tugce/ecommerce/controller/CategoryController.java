package com.tugce.ecommerce.controller;

import com.tugce.ecommerce.dto.CategoryDTO;
import com.tugce.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor

public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public CategoryDTO createCategory(@RequestBody @Valid CategoryDTO categoryDTO){
        return categoryService.createCategory(categoryDTO);
    }

    @GetMapping
    public List<CategoryDTO> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public CategoryDTO updateCategoryById(
            @PathVariable Long id,
            @RequestBody @Valid CategoryDTO categoryDTO){
        return categoryService.updateCategory(id, categoryDTO);
    }

    @DeleteMapping("/{id}")
        public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

}

