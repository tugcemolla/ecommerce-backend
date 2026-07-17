package com.tugce.ecommerce.service;

import com.tugce.ecommerce.dto.PaymentRequestDTO;
import com.tugce.ecommerce.dto.PaymentResponseDTO;
import com.tugce.ecommerce.dto.PaymentRequestDTO;
import com.tugce.ecommerce.dto.PaymentResponseDTO;

public interface PaymentService {
    PaymentResponseDTO makePayment(PaymentRequestDTO paymentRequestDTO);
}
