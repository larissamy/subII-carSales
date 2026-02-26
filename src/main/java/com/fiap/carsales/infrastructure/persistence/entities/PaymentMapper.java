package com.fiap.carsales.infrastructure.persistence.entities;

import com.fiap.carsales.domain.entities.Payment;
import com.fiap.carsales.domain.enums.PaymentStatus;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.UUID;

final class PaymentMapper {
    private PaymentMapper() {}

    static Payment rehydrate(UUID id, PaymentStatus status, String code, BigDecimal amount) {
        try {
            Constructor<Payment> c = Payment.class.getDeclaredConstructor(UUID.class, PaymentStatus.class, String.class, BigDecimal.class);
            c.setAccessible(true);
            return c.newInstance(id, status, code, amount);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to rehydrate Payment", e);
        }
    }
}
