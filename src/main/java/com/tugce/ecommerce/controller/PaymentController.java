package com.tugce.ecommerce.controller;

import com.tugce.ecommerce.dto.PaymentRequestDTO;
import com.tugce.ecommerce.dto.PaymentResponseDTO;
import com.tugce.ecommerce.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponseDTO makePayment(@Valid @RequestBody PaymentRequestDTO paymentRequestDTO){
        return paymentService.makePayment(paymentRequestDTO);
    }
}
