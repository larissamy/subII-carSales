package com.fiap.carsales.infrastructure.repositories;

import com.fiap.carsales.infrastructure.persistence.entities.PaymentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentSpringDataRepository extends JpaRepository<PaymentJpaEntity, String> {
    Optional<PaymentJpaEntity> findByPaymentCode(String paymentCode);
}
