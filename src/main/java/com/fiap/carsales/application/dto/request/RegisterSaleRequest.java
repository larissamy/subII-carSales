package com.fiap.carsales.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record RegisterSaleRequest(
        @NotNull UUID carId,
        Instant saleDate
) {}
