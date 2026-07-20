package com.tugce.ecommerce.service.impl;

import com.tugce.ecommerce.dto.GiftRequestDTO;
import com.tugce.ecommerce.dto.GiftResponseDTO;
import com.tugce.ecommerce.entity.Gift;
import com.tugce.ecommerce.mapper.GiftMapper;
import com.tugce.ecommerce.repository.GiftRepository;
import com.tugce.ecommerce.service.GiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftServiceImpl implements GiftService {
    private final GiftRepository giftRepository;
    private final GiftMapper giftMapper;

    @Override
    public GiftResponseDTO createGift(GiftRequestDTO requestDTO){
        Gift gift = giftMapper.toEntity(requestDTO);
        Gift savedGift = giftRepository.save(gift);
        return giftMapper.toResponseDTO(savedGift);
    }

    @Override
    public List<GiftResponseDTO> getAllGifts() {
        return giftRepository.findAll()
                .stream()
                .map(giftMapper::toResponseDTO)
                .toList();
    }

    @Override
    public GiftResponseDTO getGiftById(Long id) {
        Gift gift = giftRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Hediye bulunamadı. ID: " + id));

        return giftMapper.toResponseDTO(gift);
    }

    @Override
    public GiftResponseDTO updateGift(Long id, GiftRequestDTO requestDTO) {
        Gift gift = giftRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Hediye bulunamadı. ID: " + id));

        gift.setName(requestDTO.getName());
        gift.setDescription(requestDTO.getDescription());
        gift.setStock(requestDTO.getStock());
        gift.setImageUrl(requestDTO.getImageUrl());

        Gift updatedGift = giftRepository.save(gift);

        return giftMapper.toResponseDTO(updatedGift);
    }

    @Override
    public void deleteGift(Long id) {
        Gift gift = giftRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Hediye bulunamadı. ID: " + id));

        giftRepository.delete(gift);
    }

}
