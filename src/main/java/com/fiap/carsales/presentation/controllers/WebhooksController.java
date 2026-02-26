package com.fiap.carsales.presentation.controllers;

import com.fiap.carsales.application.dto.request.PaymentWebhookRequest;
import com.fiap.carsales.application.interfaces.PaymentConfirmationPort;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhooks/payments")
public class WebhooksController {

    private final PaymentConfirmationPort service;

    public WebhooksController(PaymentConfirmationPort service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> processPayment(@Valid @RequestBody PaymentWebhookRequest request) {
        service.confirm(request);
        return ResponseEntity.noContent().build();
    }
}
