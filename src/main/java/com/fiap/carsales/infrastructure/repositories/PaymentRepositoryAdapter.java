package com.fiap.carsales.infrastructure.repositories;

import com.fiap.carsales.domain.entities.Payment;
import com.fiap.carsales.domain.repositories.PaymentRepository;
import com.fiap.carsales.infrastructure.persistence.entities.PaymentJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PaymentRepositoryAdapter implements PaymentRepository {

    private final PaymentSpringDataRepository repo;

    public PaymentRepositoryAdapter(PaymentSpringDataRepository repo) {
        this.repo = repo;
    }

    @Override
    public void add(Payment payment) {
        repo.save(PaymentJpaEntity.fromDomain(payment));
    }

    @Override
    public void update(Payment payment) {
        repo.save(PaymentJpaEntity.fromDomain(payment));
    }

    @Override
    public Optional<Payment> getByPaymentCode(String code) {
        return repo.findByPaymentCode(code).map(PaymentJpaEntity::toDomain);
    }
}
