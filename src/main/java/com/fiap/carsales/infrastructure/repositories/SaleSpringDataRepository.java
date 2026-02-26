package com.fiap.carsales.infrastructure.repositories;

import com.fiap.carsales.infrastructure.persistence.entities.SaleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaleSpringDataRepository extends JpaRepository<SaleJpaEntity, String> {
    Optional<SaleJpaEntity> findByPaymentId(String paymentId);
}
