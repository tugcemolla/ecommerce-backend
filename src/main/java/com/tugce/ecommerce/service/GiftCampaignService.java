package com.tugce.ecommerce.service;

import com.tugce.ecommerce.dto.GiftCampaignRequestDTO;
import com.tugce.ecommerce.dto.GiftCampaignResponseDTO;

import java.util.List;

public interface GiftCampaignService {

    GiftCampaignResponseDTO createCampaign(GiftCampaignRequestDTO requestDTO);

    List<GiftCampaignResponseDTO> getAllCampaigns();

    GiftCampaignResponseDTO getCampaignById(Long id);

    GiftCampaignResponseDTO updateCampaign(Long id, GiftCampaignRequestDTO requestDTO);

    void deleteCampaign(Long id);
}