package com.tugce.ecommerce.service.impl;

import com.tugce.ecommerce.dto.GiftCampaignRequestDTO;
import com.tugce.ecommerce.dto.GiftCampaignResponseDTO;
import com.tugce.ecommerce.entity.Category;
import com.tugce.ecommerce.entity.Gift;
import com.tugce.ecommerce.entity.GiftCampaign;
import com.tugce.ecommerce.mapper.GiftCampaignMapper;
import com.tugce.ecommerce.repository.CategoryRepository;
import com.tugce.ecommerce.repository.GiftCampaignRepository;
import com.tugce.ecommerce.repository.GiftRepository;
import com.tugce.ecommerce.service.GiftCampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftCampaignServiceImpl implements GiftCampaignService {

    private final GiftCampaignRepository giftCampaignRepository;
    private final CategoryRepository categoryRepository;
    private final GiftRepository giftRepository;
    private final GiftCampaignMapper giftCampaignMapper;

    @Override
    public GiftCampaignResponseDTO createCampaign(
            GiftCampaignRequestDTO requestDTO) {

        Category category = categoryRepository
                .findById(requestDTO.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Kategori bulunamadı. ID: "
                                        + requestDTO.getCategoryId()));

        Gift gift = giftRepository
                .findById(requestDTO.getGiftId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Hediye bulunamadı. ID: "
                                        + requestDTO.getGiftId()));

        GiftCampaign campaign = GiftCampaign.builder()
                .category(category)
                .gift(gift)
                .minimumAmount(requestDTO.getMinimumAmount())
                .active(requestDTO.getActive())
                .build();

        GiftCampaign savedCampaign =
                giftCampaignRepository.save(campaign);

        return giftCampaignMapper.toResponseDTO(savedCampaign);
    }

    @Override
    public List<GiftCampaignResponseDTO> getAllCampaigns() {
        return giftCampaignRepository.findAll()
                .stream()
                .map(giftCampaignMapper::toResponseDTO)
                .toList();
    }

    @Override
    public GiftCampaignResponseDTO getCampaignById(Long id) {
        GiftCampaign campaign = giftCampaignRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Hediye kampanyası bulunamadı. ID: " + id));

        return giftCampaignMapper.toResponseDTO(campaign);
    }

    @Override
    public GiftCampaignResponseDTO updateCampaign(
            Long id,
            GiftCampaignRequestDTO requestDTO) {

        GiftCampaign campaign = giftCampaignRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Hediye kampanyası bulunamadı. ID: " + id));

        Category category = categoryRepository
                .findById(requestDTO.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Kategori bulunamadı. ID: "
                                        + requestDTO.getCategoryId()));

        Gift gift = giftRepository
                .findById(requestDTO.getGiftId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Hediye bulunamadı. ID: "
                                        + requestDTO.getGiftId()));

        campaign.setCategory(category);
        campaign.setGift(gift);
        campaign.setMinimumAmount(requestDTO.getMinimumAmount());
        campaign.setActive(requestDTO.getActive());

        GiftCampaign updatedCampaign =
                giftCampaignRepository.save(campaign);

        return giftCampaignMapper.toResponseDTO(updatedCampaign);
    }

    @Override
    public void deleteCampaign(Long id) {
        GiftCampaign campaign = giftCampaignRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Hediye kampanyası bulunamadı. ID: " + id));

        giftCampaignRepository.delete(campaign);
    }
}