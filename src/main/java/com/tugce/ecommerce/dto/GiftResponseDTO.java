package com.tugce.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Integer stock;
    private String imageUrl;
}
