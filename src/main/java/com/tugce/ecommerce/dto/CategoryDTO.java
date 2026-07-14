package com.tugce.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CategoryDTO {
    private Long id;

    @NotBlank(message = "Kategori adı boş olamaz.")
    private String name;
}
