package com.tugce.ecommerce.service.impl;

import com.tugce.ecommerce.dto.AddToCartRequestDTO;
import com.tugce.ecommerce.dto.CartResponseDTO;
import com.tugce.ecommerce.entity.Cart;
import com.tugce.ecommerce.entity.CartItem;
import com.tugce.ecommerce.entity.Product;
import com.tugce.ecommerce.entity.User;
import com.tugce.ecommerce.mapper.CartMapper;
import com.tugce.ecommerce.repository.CartItemRepository;
import com.tugce.ecommerce.repository.CartRepository;
import com.tugce.ecommerce.repository.ProductRepository;
import com.tugce.ecommerce.repository.UserRepository;
import com.tugce.ecommerce.service.CartService;
import com.tugce.ecommerce.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
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
    public CartResponseDTO addToCart(String email, AddToCartRequestDTO requestDTO){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));
        Product product = productRepository
                .findById(requestDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı."));
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
                    .build();
        }
        cartItemRepository.save(cartItem);
        cart.getItems().removeIf(
                item -> item.getProduct()
                        .getId()
                        .equals(product.getId())
        );
        cart.getItems().add(cartItem);
        return cartMapper.tocartResponseDTO(cart);
    }

    @Override
    public CartResponseDTO getCart(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Sepet bulunamadı."));
        return cartMapper.tocartResponseDTO(cart);
    }

    @Override
    public CartResponseDTO updateQuantity(String email, Long productId, Integer quantity){
        if(quantity == null || quantity <= 0){
            throw new RuntimeException("Ürün adedi 0'dan büyük olmalıdır.");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Sepet bulunamadı"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı."));
        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("Ürün sepette bulunamadı"));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        cart.getItems().removeIf(
                item -> item.getProduct()
                        .getId()
                        .equals(productId)
        );

        cart.getItems().add(cartItem);
        return cartMapper.tocartResponseDTO(cart);
    }

    @Override
    public CartResponseDTO removeFromCart(String email, Long productId){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Sepet bulunamadı."));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı."));
        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("Ürün sepette bulunamadı."));
        cartItemRepository.delete(cartItem);
        cart.getItems().removeIf(
                item -> item.getProduct()
                        .getId()
                        .equals(productId)
        );
        return cartMapper.tocartResponseDTO(cart);
    }

    @Override
    public void clearCart(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Sepet bulunamadı."));
        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();;

    }
}
