package com.fiap.carsales.application.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateCarRequest(
        @NotBlank String brand,
        @NotBlank String model,
        @Min(1900) int year,
        @NotBlank String color,
        @DecimalMin(value = "0.01") BigDecimal price
) {}
