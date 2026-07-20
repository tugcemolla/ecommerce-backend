package com.tugce.ecommerce.mapper;

import com.tugce.ecommerce.dto.GiftRequestDTO;
import com.tugce.ecommerce.dto.GiftResponseDTO;
import com.tugce.ecommerce.entity.Gift;
import org.springframework.stereotype.Component;

@Component
public class GiftMapper {
    public Gift toEntity(GiftRequestDTO requestDTO){
        return Gift.builder()
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .stock(requestDTO.getStock())
                .imageUrl(requestDTO.getImageUrl())
                .build();
    }

    public GiftResponseDTO toResponseDTO(Gift gift){
        return GiftResponseDTO.builder()
                .id(gift.getId())
                .name(gift.getName())
                .description(gift.getDescription())
                .stock(gift.getStock())
                .imageUrl(gift.getImageUrl())
                .build();
    }
}
