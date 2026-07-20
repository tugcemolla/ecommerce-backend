package com.tugce.ecommerce.service.impl;

import com.tugce.ecommerce.dto.AddToCartRequestDTO;
import com.tugce.ecommerce.dto.CartResponseDTO;
import com.tugce.ecommerce.entity.Cart;
import com.tugce.ecommerce.entity.CartItem;
import com.tugce.ecommerce.entity.Product;
import com.tugce.ecommerce.entity.User;
import com.tugce.ecommerce.exception.ResourceNotFoundException;
import com.tugce.ecommerce.mapper.CartMapper;
import com.tugce.ecommerce.repository.CartItemRepository;
import com.tugce.ecommerce.repository.CartRepository;
import com.tugce.ecommerce.repository.ProductRepository;
import com.tugce.ecommerce.repository.UserRepository;
import com.tugce.ecommerce.service.CartService;
import com.tugce.ecommerce.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CartServiceImpl implements CartService {
    private static final BigDecimal GIFT_LIMIT = new BigDecimal("20000");
    private static final Long GIFT_PRODUCT_ID = 1L;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartServiceImpl(UserRepository userRepository, CartRepository cartRepository, CartItemRepository cartItemRepository, ProductService productService, CartMapper cartMapper, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    @Transactional
    public CartResponseDTO addToCart(String email, AddToCartRequestDTO requestDTO){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->   new ResourceNotFoundException("Kullanıcı bulunamadı."));
        Product product = productRepository
                .findById(requestDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Ürün bulunamadı."));
        if(product.getId().equals(GIFT_PRODUCT_ID)){
            throw new RuntimeException("Hediye ürün sepete manuel olarak eklenemez.");
        }
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
        if(cartItem != null){
            cartItem.setQuantity(
                    cartItem.getQuantity() + requestDTO.getQuantity()
            );
        }else {
            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(requestDTO.getQuantity())
                    .gift(false)
                    .build();
        }
        cartItemRepository.save(cartItem);
        cart.getItems().removeIf(
                item -> item.getProduct()
                        .getId()
                        .equals(product.getId())
        );
        cart.getItems().add(cartItem);
        applyGiftCampaign(cart);
        return cartMapper.tocartResponseDTO(cart);
    }

    @Override
    public CartResponseDTO getCart(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->   new ResourceNotFoundException("Kullanıcı bulunamadı."));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->  new ResourceNotFoundException("Sepet bulunamadı."));
        applyGiftCampaign(cart);
        return cartMapper.tocartResponseDTO(cart);
    }

    @Override
    @Transactional
    public CartResponseDTO updateQuantity(String email, Long productId, Integer quantity){
        if(quantity == null || quantity <= 0){
            throw new RuntimeException("Ürün adedi 0'dan büyük olmalıdır.");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->   new ResourceNotFoundException("Kullanıcı bulunamadı."));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->  new ResourceNotFoundException("Sepet bulunamadı."));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Ürün bulunamadı."));
        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() -> new ResourceNotFoundException("Ürün sepette bulunamadı."));
        if(Boolean.TRUE.equals(cartItem.getGift())){
            throw new RuntimeException("Hediye ürününün adedi değiştirilemez.");
        }
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        cart.getItems().removeIf(
                item -> item.getProduct()
                        .getId()
                        .equals(productId)
        );

        cart.getItems().add(cartItem);
        applyGiftCampaign(cart);
        return cartMapper.tocartResponseDTO(cart);
    }

    @Override
    @Transactional
    public CartResponseDTO removeFromCart(String email, Long productId){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->   new ResourceNotFoundException("Kullanıcı bulunamadı."));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Sepet bulunamadı."));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Ürün bulunamadı."));
        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() ->  new ResourceNotFoundException("Ürün sepette bulunamadı."));
        if(Boolean.TRUE.equals(cartItem.getGift())){
            throw new RuntimeException("Hediye ürün sepetten manuel olarak silinemez.");
        }
        cartItemRepository.delete(cartItem);
        cart.getItems().removeIf(
                item -> item.getProduct()
                        .getId()
                        .equals(productId)
        );
        applyGiftCampaign(cart);
        return cartMapper.tocartResponseDTO(cart);
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
    private void applyGiftCampaign(Cart cart){
        BigDecimal normalProductTotal = cart.getItems()
                .stream()
                .filter(item -> !Boolean.TRUE.equals(item.getGift()))
                .map(item ->
                        item.getProduct()
                                .getPrice()
                                .multiply(
                                        BigDecimal.valueOf(item.getQuantity())
                                )
                )
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );
CartItem existingGift = cart.getItems()
        .stream()
        .filter(item -> Boolean.TRUE.equals(item.getGift()))
        .findFirst()
        .orElse(null);
if(normalProductTotal.compareTo(GIFT_LIMIT) >= 0){
    if (existingGift == null) {
        Product giftProduct = productRepository
                .findById(GIFT_PRODUCT_ID)
                .orElseThrow(() -> new ResourceNotFoundException("Kampanya hediye ürünü bulunamadı."));
        CartItem giftTeam = CartItem.builder()
                .cart(cart)
                .product(giftProduct)
                .quantity(1)
                .gift(true)
                .build();
        cartItemRepository.save(giftTeam);
        cart.getItems().add(giftTeam);
    }else{
        if(existingGift != null){
            cartItemRepository.delete(existingGift);
            cart.getItems().remove(existingGift);
        }
    }
}
    }

}
