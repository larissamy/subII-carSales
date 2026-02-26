package com.fiap.carsales.infrastructure.persistence.entities;

import com.fiap.carsales.domain.entities.Sale;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

final class SaleMapper {
    private SaleMapper() {}

    static Sale rehydrate(UUID id, UUID carId, UUID paymentId, BigDecimal price, String taxId, Instant createdAt) {
        try {
            Constructor<Sale> c = Sale.class.getDeclaredConstructor(UUID.class, UUID.class, UUID.class, BigDecimal.class, String.class, Instant.class);
            c.setAccessible(true);
            return c.newInstance(id, carId, paymentId, price, taxId, createdAt);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to rehydrate Sale", e);
        }
    }
}
