package com.fiap.carsales.presentation.controllers;

import com.fiap.carsales.application.interfaces.PaymentServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentServicePort service;

    public PaymentController(PaymentServicePort service) {
        this.service = service;
    }

    @GetMapping("/{paymentCode}")
    public ResponseEntity<?> getPayment(@PathVariable String paymentCode) {
        return service.getPaymentByPaymentCode(paymentCode)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
