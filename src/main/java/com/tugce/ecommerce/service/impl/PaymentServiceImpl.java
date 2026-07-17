package com.tugce.ecommerce.service.impl;

import com.tugce.ecommerce.dto.PaymentRequestDTO;
import com.tugce.ecommerce.dto.PaymentResponseDTO;
import com.tugce.ecommerce.dto.PaymentRequestDTO;
import com.tugce.ecommerce.dto.PaymentResponseDTO;
import com.tugce.ecommerce.entity.Order;
import com.tugce.ecommerce.entity.OrderStatus;
import com.tugce.ecommerce.entity.Payment;
import com.tugce.ecommerce.entity.PaymentStatus;
import com.tugce.ecommerce.exception.ResourceNotFoundException;
import com.tugce.ecommerce.mapper.PaymentMapper;
import com.tugce.ecommerce.repository.OrderRepository;
import com.tugce.ecommerce.repository.PaymentRepository;
import com.tugce.ecommerce.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponseDTO makePayment(PaymentRequestDTO paymentRequestDTO){
        Order order = orderRepository.findById(paymentRequestDTO.getOrderId())
                .orElseThrow(() ->   new ResourceNotFoundException("Sipariş bulunamadı."));
        Payment payment = Payment.builder()
                .order(order)
                .amount(order.getTotalAmount())
                .status(PaymentStatus.SUCCESS)
                .paymentDate(LocalDateTime.now())
                .build();
        order.setStatus(OrderStatus.PREPARING);
        orderRepository.save(order);
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponseDTO(savedPayment);
    }

}
