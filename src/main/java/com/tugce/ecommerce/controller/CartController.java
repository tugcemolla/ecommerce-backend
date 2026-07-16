package com.tugce.ecommerce.controller;

import com.tugce.ecommerce.dto.AddToCartRequestDTO;
import com.tugce.ecommerce.dto.CartResponseDTO;
import com.tugce.ecommerce.service.CartService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public CartResponseDTO addToCart(
            Authentication authentication,
            @RequestBody @Valid AddToCartRequestDTO requestDTO){
        String email = authentication.getName();
        return cartService.addToCart(email, requestDTO);
    }

    @GetMapping
    public CartResponseDTO getCart(Authentication authentication){
        String email = authentication.getName();
        return cartService.getCart(email);
    }

    @PutMapping("/update")
    public CartResponseDTO updateQuantity(
            Authentication authentication,
            @RequestParam Long productId,
            @RequestParam Integer quantity){
        String email = authentication.getName();
        return cartService.updateQuantity(
                email,
                productId,
                quantity
        );
    }

    @DeleteMapping("/remove")
    public CartResponseDTO removeFromCart(
            Authentication authentication,
            @RequestParam Long productId){
        String email = authentication.getName();
        return cartService.removeFromCart(email, productId);
    }

    @DeleteMapping("/clear")
    public void clearCart(Authentication authentication){
        String email = authentication.getName();
        cartService.clearCart(email);
    }
}
