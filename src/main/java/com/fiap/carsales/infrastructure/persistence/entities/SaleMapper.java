package com.fiap.carsales.infrastructure.persistence.entities;

import com.fiap.carsales.domain.entities.Sale;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

final class SaleMapper {
    private SaleMapper() {
    }

    static Sale rehydrate(UUID id,
                          UUID carId,
                          UUID paymentId,
                          UUID buyerId,
                          String buyerEmail,
                          BigDecimal price,
                          Instant createdAt) {
        try {
            Constructor<Sale> c = Sale.class.getDeclaredConstructor(
                    UUID.class,
                    UUID.class,
                    UUID.class,
                    UUID.class,
                    String.class,
                    BigDecimal.class,
                    Instant.class
            );
            c.setAccessible(true);
            return c.newInstance(id, carId, paymentId, buyerId, buyerEmail, price, createdAt);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to rehydrate Sale", e);
        }
    }
}