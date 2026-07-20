package com.tugce.ecommerce.service;

import com.tugce.ecommerce.dto.GiftRequestDTO;
import com.tugce.ecommerce.dto.GiftResponseDTO;

import java.util.List;

public interface GiftService {
    GiftResponseDTO createGift(GiftRequestDTO requestDTO);
    List<GiftResponseDTO> getAllGifts();
    GiftResponseDTO getGiftById(Long id);
    GiftResponseDTO updateGift(Long id, GiftRequestDTO requestDTO);
    void deleteGift(Long id);


}
