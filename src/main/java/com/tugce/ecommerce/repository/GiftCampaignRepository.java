package com.tugce.ecommerce.repository;

import com.tugce.ecommerce.entity.Category;
import com.tugce.ecommerce.entity.GiftCampaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GiftCampaignRepository extends JpaRepository<GiftCampaign, Long> {

    Optional<GiftCampaign> findByCategoryAndActiveTrue(Category category);
}