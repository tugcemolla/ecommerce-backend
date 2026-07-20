package com.tugce.ecommerce.mapper;

import com.tugce.ecommerce.dto.GiftCampaignResponseDTO;
import com.tugce.ecommerce.entity.GiftCampaign;
import org.springframework.stereotype.Component;

@Component
public class GiftCampaignMapper {

    public GiftCampaignResponseDTO toResponseDTO(GiftCampaign campaign) {
        return GiftCampaignResponseDTO.builder()
                .id(campaign.getId())
                .categoryId(campaign.getCategory().getId())
                .categoryName(campaign.getCategory().getName())
                .giftId(campaign.getGift().getId())
                .giftName(campaign.getGift().getName())
                .minimumAmount(campaign.getMinimumAmount())
                .active(campaign.getActive())
                .build();
    }
}