package com.fiap.carsales.application.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CarResponse(
        UUID id,
        String licensePlate,
        String model,
        int year,
        BigDecimal price,
        Instant dateUpdate
) {}
