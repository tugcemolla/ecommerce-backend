package com.tugce.ecommerce.mapper;

import com.tugce.ecommerce.dto.PaymentResponseDTO;
import com.tugce.ecommerce.entity.Payment;
import org.springframework.stereotype.Component;

 @Component
public class PaymentMapper {
     public PaymentResponseDTO toResponseDTO(Payment payment){
         return PaymentResponseDTO.builder()
                 .paymentId(payment.getId())
                 .orderId(payment.getOrder().getId())
                 .amount(payment.getAmount())
                 .status(payment.getStatus())
                 .paymentDate(payment.getPaymentDate())
                 .build();
     }
}
