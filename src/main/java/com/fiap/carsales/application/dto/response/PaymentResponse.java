package com.fiap.carsales.application.dto.response;

import com.fiap.carsales.domain.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentResponse(
        UUID id,
        BigDecimal amount,
        String paymentCode,
        PaymentStatus status
) {}
