package com.tugce.ecommerce.service.impl;

import com.tugce.ecommerce.dto.OrderResponseDTO;
import com.tugce.ecommerce.entity.Cart;
import com.tugce.ecommerce.entity.User;
import com.tugce.ecommerce.exception.ResourceNotFoundException;
import com.tugce.ecommerce.mapper.OrderMapper;
import com.tugce.ecommerce.repository.CartItemRepository;
import com.tugce.ecommerce.repository.CartRepository;
import com.tugce.ecommerce.repository.OrderRepository;
import com.tugce.ecommerce.repository.UserRepository;
import com.tugce.ecommerce.entity.Order;
import com.tugce.ecommerce.entity.OrderStatus;
import com.tugce.ecommerce.service.OrderService;
import com.tugce.ecommerce.entity.OrderItem;
import com.tugce.ecommerce.entity.CartItem;
import org.springframework.stereotype.Service;


import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(
            UserRepository userRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            OrderRepository orderRepository,
            OrderMapper orderMapper) {

        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponseDTO createOrder(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->   new ResourceNotFoundException("Kullanıcı bulunamadı."));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->new ResourceNotFoundException("Sepet bulunamadı."));
        if(cart.getItems().isEmpty()){
            throw new RuntimeException("Sepet boş. Sipariş oluşturulamaz.");
        }
        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .totalAmount(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .build();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(CartItem cartItem : cart.getItems()){
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .unitPrice(cartItem.getProduct().getPrice())
                    .totalPrice(
                            cartItem.getProduct()
                                    .getPrice()
                                    .multiply(
                                            BigDecimal.valueOf(
                                                    cartItem.getQuantity()
                                            )
                                    )
                    )
                    .build();
            order.getItems().add(orderItem);
            totalAmount = totalAmount.add(orderItem.getTotalPrice());
        }
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();;
        return orderMapper.toOrderResponseDTO(order);
    }

    @Override
    public List<OrderResponseDTO> getMyOrders(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->   new ResourceNotFoundException("Kullanıcı bulunamadı."));

        return orderRepository
                .findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(orderMapper::toOrderResponseDTO)//her siparişi DTO'ya dönüştürür.
                .toList();

    }

    @Override
    public OrderResponseDTO getOrderById(String email, Long orderId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->   new ResourceNotFoundException("Kullanıcı bulunamadı."));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->   new ResourceNotFoundException("Sipariş bulunamadı."));
        if(!order.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Bu siparişi görüntüleme yetkiniz yok.");
        }
        return orderMapper.toOrderResponseDTO(order);
    }
}