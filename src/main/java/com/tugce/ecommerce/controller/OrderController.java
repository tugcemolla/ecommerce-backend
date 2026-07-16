package com.tugce.ecommerce.controller;

import com.tugce.ecommerce.dto.OrderResponseDTO;
import com.tugce.ecommerce.service.OrderService;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponseDTO createOrder(Authentication authentication){
        return orderService.createOrder(authentication.getName());
    }

    @GetMapping
    public List<OrderResponseDTO> getMyOrders(Authentication authentication){
        return orderService.getMyOrders(authentication.getName());
    }

    @GetMapping("/{orderId}")
    public OrderResponseDTO getOrderById(
            @PathVariable Long orderId,
            Authentication authentication){
        return orderService.getOrderById(
                authentication.getName(),
                orderId
        );
    }
}
