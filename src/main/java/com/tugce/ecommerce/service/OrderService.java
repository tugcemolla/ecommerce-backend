package com.tugce.ecommerce.service;

import com.tugce.ecommerce.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(String email);
    List<OrderResponseDTO> getMyOrders(String email);
    OrderResponseDTO getOrderById(String email, Long orderId);
}
