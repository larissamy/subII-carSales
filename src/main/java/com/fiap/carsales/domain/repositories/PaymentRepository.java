package com.fiap.carsales.domain.repositories;

import com.fiap.carsales.domain.entities.Payment;

import java.util.Optional;

public interface PaymentRepository {
    void add(Payment payment);
    void update(Payment payment);
    Optional<Payment> getByPaymentCode(String code);
}
