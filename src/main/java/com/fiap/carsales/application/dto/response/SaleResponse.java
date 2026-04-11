package com.fiap.carsales.application.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record SaleResponse(
        UUID id,
        UUID carId,
        UUID buyerId,
        String buyerEmail,
        BigDecimal price,
        String paymentCode,
        Instant createdAt,
        String paymentStatus
) {}