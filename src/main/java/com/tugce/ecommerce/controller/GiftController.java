package com.tugce.ecommerce.controller;

import com.tugce.ecommerce.dto.GiftRequestDTO;
import com.tugce.ecommerce.dto.GiftResponseDTO;
import com.tugce.ecommerce.service.GiftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gifts")
@RequiredArgsConstructor
public class GiftController {

    private final GiftService giftService;

    // Hediye Ekle
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftResponseDTO createGift(@Valid @RequestBody GiftRequestDTO requestDTO) {
        return giftService.createGift(requestDTO);
    }

    // Tüm Hediyeleri Listele
    @GetMapping
    public List<GiftResponseDTO> getAllGifts() {
        return giftService.getAllGifts();
    }

    // ID'ye Göre Hediye Getir
    @GetMapping("/{id}")
    public GiftResponseDTO getGiftById(@PathVariable Long id) {
        return giftService.getGiftById(id);
    }

    // Hediye Güncelle
    @PutMapping("/{id}")
    public GiftResponseDTO updateGift(@PathVariable Long id,
                                      @Valid @RequestBody GiftRequestDTO requestDTO) {
        return giftService.updateGift(id, requestDTO);
    }

    // Hediye Sil
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGift(@PathVariable Long id) {
        giftService.deleteGift(id);
    }
}