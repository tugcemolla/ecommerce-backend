package com.tugce.ecommerce.mapper;

import com.tugce.ecommerce.dto.OrderItemResponseDTO;
import com.tugce.ecommerce.dto.OrderResponseDTO;
import com.tugce.ecommerce.entity.Order;
import com.tugce.ecommerce.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderItemResponseDTO toItemResponseDTO(OrderItem orderItem) {

        return OrderItemResponseDTO.builder()
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProduct().getName())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }

    public OrderResponseDTO toOrderResponseDTO(Order order) {

        List<OrderItemResponseDTO> itemDTOs = order.getItems()
                .stream()
                .map(this::toItemResponseDTO)
                .toList();

        return OrderResponseDTO.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .items(itemDTOs)
                .build();
    }
}