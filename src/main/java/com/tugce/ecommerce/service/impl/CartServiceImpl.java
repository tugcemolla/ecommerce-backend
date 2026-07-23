package com.tugce.ecommerce.service.impl;

import com.tugce.ecommerce.dto.AddToCartRequestDTO;
import com.tugce.ecommerce.dto.CartResponseDTO;
import com.tugce.ecommerce.entity.Cart;
import com.tugce.ecommerce.entity.CartItem;
import com.tugce.ecommerce.entity.GiftCampaign;
import com.tugce.ecommerce.entity.Product;
import com.tugce.ecommerce.entity.User;
import com.tugce.ecommerce.exception.ResourceNotFoundException;
import com.tugce.ecommerce.mapper.CartMapper;
import com.tugce.ecommerce.repository.CartItemRepository;
import com.tugce.ecommerce.repository.CartRepository;
import com.tugce.ecommerce.repository.GiftCampaignRepository;
import com.tugce.ecommerce.repository.ProductRepository;
import com.tugce.ecommerce.repository.UserRepository;
import com.tugce.ecommerce.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final GiftCampaignRepository giftCampaignRepository;
    private final CartMapper cartMapper;

    public CartServiceImpl(
            UserRepository userRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            GiftCampaignRepository giftCampaignRepository,
            CartMapper cartMapper
    ) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.giftCampaignRepository = giftCampaignRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    @Transactional
    public CartResponseDTO addToCart(
            String email,
            AddToCartRequestDTO requestDTO
    ) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Kullanıcı bulunamadı."));

        Product product = productRepository.findById(requestDTO.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Ürün bulunamadı."));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .user(user)
                            .build();

                    return cartRepository.save(newCart);
                });

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(
                    cartItem.getQuantity() + requestDTO.getQuantity()
            );
        } else {
            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .giftProduct(null)
                    .quantity(requestDTO.getQuantity())
                    .gift(false)
                    .build();
        }

        cartItemRepository.save(cartItem);

        CartItem savedCartItem = cartItem;

        cart.getItems().removeIf(item ->
                item.getProduct() != null
                        && item.getProduct().getId()
                        .equals(product.getId())
        );

        cart.getItems().add(savedCartItem);

        applyGiftCampaign(cart);

        return cartMapper.toCartResponseDTO(cart);
    }

    @Override
    @Transactional
    public CartResponseDTO getCart(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Kullanıcı bulunamadı."));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sepet bulunamadı."));

        applyGiftCampaign(cart);

        return cartMapper.toCartResponseDTO(cart);
    }

    @Override
    @Transactional
    public CartResponseDTO updateQuantity(
            String email,
            Long productId,
            Integer quantity
    ) {
        if (quantity == null || quantity <= 0) {
            throw new RuntimeException(
                    "Ürün adedi 0'dan büyük olmalıdır."
            );
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Kullanıcı bulunamadı."));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sepet bulunamadı."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Ürün bulunamadı."));

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ürün sepette bulunamadı."
                        ));

        if (Boolean.TRUE.equals(cartItem.getGift())) {
            throw new RuntimeException(
                    "Hediye ürününün adedi değiştirilemez."
            );
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        cart.getItems().removeIf(item ->
                item.getProduct() != null
                        && item.getProduct().getId().equals(productId)
        );

        cart.getItems().add(cartItem);

        applyGiftCampaign(cart);

        return cartMapper.toCartResponseDTO(cart);
    }

    @Override
    @Transactional
    public CartResponseDTO removeFromCart(
            String email,
            Long productId
    ) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Kullanıcı bulunamadı."));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sepet bulunamadı."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Ürün bulunamadı."));

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ürün sepette bulunamadı."
                        ));

        if (Boolean.TRUE.equals(cartItem.getGift())) {
            throw new RuntimeException(
                    "Hediye ürün sepetten manuel olarak silinemez."
            );
        }

        cartItemRepository.delete(cartItem);

        cart.getItems().removeIf(item ->
                item.getProduct() != null
                        && item.getProduct().getId().equals(productId)
        );

        applyGiftCampaign(cart);

        return cartMapper.toCartResponseDTO(cart);
    }

    @Override
    @Transactional
    public void clearCart(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Kullanıcı bulunamadı."));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sepet bulunamadı."));

        cartItemRepository.deleteAll(cart.getItems());

        cart.getItems().clear();

        cartRepository.save(cart);
    }

    private void applyGiftCampaign(Cart cart) {

        BigDecimal normalProductTotal = cart.getItems()
                .stream()
                .filter(item ->
                        !Boolean.TRUE.equals(item.getGift())
                                && item.getProduct() != null
                )
                .map(item ->
                        item.getProduct()
                                .getPrice()
                                .multiply(
                                        BigDecimal.valueOf(
                                                item.getQuantity()
                                        )
                                )
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CartItem existingGift = cart.getItems()
                .stream()
                .filter(item ->
                        Boolean.TRUE.equals(item.getGift())
                )
                .findFirst()
                .orElse(null);

        Optional<CartItem> mainCartItemOptional = cart.getItems()
                .stream()
                .filter(item ->
                        !Boolean.TRUE.equals(item.getGift())
                                && item.getProduct() != null
                                && item.getProduct().getCategory() != null
                )
                .max(Comparator.comparing(item ->
                        item.getProduct()
                                .getPrice()
                                .multiply(
                                        BigDecimal.valueOf(
                                                item.getQuantity()
                                        )
                                )
                ));

        if (mainCartItemOptional.isEmpty()) {
            removeExistingGift(cart, existingGift);
            return;
        }

        Product mainProduct =
                mainCartItemOptional.get().getProduct();

        Optional<GiftCampaign> campaignOptional =
                giftCampaignRepository
                        .findByCategoryAndActiveTrue(
                                mainProduct.getCategory()
                        );

        if (campaignOptional.isEmpty()) {
            removeExistingGift(cart, existingGift);
            return;
        }

        GiftCampaign campaign = campaignOptional.get();

        if (normalProductTotal.compareTo(
                campaign.getMinimumAmount()
        ) < 0) {
            removeExistingGift(cart, existingGift);
            return;
        }

        if (campaign.getGift().getStock() <= 0) {
            removeExistingGift(cart, existingGift);
            return;
        }

        if (existingGift != null
                && existingGift.getGiftProduct() != null
                && existingGift.getGiftProduct()
                .getId()
                .equals(campaign.getGift().getId())) {
            return;
        }

        removeExistingGift(cart, existingGift);

        CartItem giftItem = CartItem.builder()
                .cart(cart)
                .product(null)
                .giftProduct(campaign.getGift())
                .quantity(1)
                .gift(true)
                .build();

        cartItemRepository.save(giftItem);

        cart.getItems().add(giftItem);
    }

    private void removeExistingGift(
            Cart cart,
            CartItem existingGift
    ) {
        if (existingGift != null) {
            cartItemRepository.delete(existingGift);
            cart.getItems().remove(existingGift);
        }
    }
}