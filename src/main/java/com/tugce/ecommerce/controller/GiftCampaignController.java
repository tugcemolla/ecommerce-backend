package com.tugce.ecommerce.controller;

import com.tugce.ecommerce.dto.GiftCampaignRequestDTO;
import com.tugce.ecommerce.dto.GiftCampaignResponseDTO;
import com.tugce.ecommerce.service.GiftCampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gift-campaigns")
@RequiredArgsConstructor
public class GiftCampaignController {

    private final GiftCampaignService giftCampaignService;

    // Yeni hediye kampanyası oluşturur
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCampaignResponseDTO createCampaign(
            @Valid @RequestBody GiftCampaignRequestDTO requestDTO) {

        return giftCampaignService.createCampaign(requestDTO);
    }

    // Tüm hediye kampanyalarını getirir
    @GetMapping
    public List<GiftCampaignResponseDTO> getAllCampaigns() {
        return giftCampaignService.getAllCampaigns();
    }

    // ID'ye göre hediye kampanyasını getirir
    @GetMapping("/{id}")
    public GiftCampaignResponseDTO getCampaignById(
            @PathVariable Long id) {

        return giftCampaignService.getCampaignById(id);
    }

    // Hediye kampanyasını günceller
    @PutMapping("/{id}")
    public GiftCampaignResponseDTO updateCampaign(
            @PathVariable Long id,
            @Valid @RequestBody GiftCampaignRequestDTO requestDTO) {

        return giftCampaignService.updateCampaign(id, requestDTO);
    }

    // Hediye kampanyasını siler
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCampaign(@PathVariable Long id) {
        giftCampaignService.deleteCampaign(id);
    }
}