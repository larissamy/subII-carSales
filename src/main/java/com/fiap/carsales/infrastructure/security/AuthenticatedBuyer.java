package com.fiap.carsales.infrastructure.security;

import java.util.UUID;

public record AuthenticatedBuyer(
        UUID buyerId,
        String email
) {
}