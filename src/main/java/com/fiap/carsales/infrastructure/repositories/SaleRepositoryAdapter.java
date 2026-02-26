package com.fiap.carsales.infrastructure.repositories;

import com.fiap.carsales.domain.entities.Car;
import com.fiap.carsales.domain.entities.Payment;
import com.fiap.carsales.domain.entities.Sale;
import com.fiap.carsales.domain.repositories.SaleRepository;
import com.fiap.carsales.infrastructure.persistence.entities.PaymentJpaEntity;
import com.fiap.carsales.infrastructure.persistence.entities.SaleJpaEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public class SaleRepositoryAdapter implements SaleRepository {

    private final SaleSpringDataRepository saleRepo;
    private final PaymentSpringDataRepository paymentRepo;
    private final CarSpringDataRepository carRepo;

    public SaleRepositoryAdapter(SaleSpringDataRepository saleRepo, PaymentSpringDataRepository paymentRepo, CarSpringDataRepository carRepo) {
        this.saleRepo = saleRepo;
        this.paymentRepo = paymentRepo;
        this.carRepo = carRepo;
    }

    @Override
    public void add(Sale sale) {
        saleRepo.save(SaleJpaEntity.fromDomain(sale));
    }

    @Override
    public void update(Sale sale) {
        saleRepo.save(SaleJpaEntity.fromDomain(sale));
    }

    @Override
    public Optional<Sale> getById(UUID id) {
        return saleRepo.findById(id.toString()).map(SaleJpaEntity::toDomain);
    }

    @Override
    @Transactional
    public void createSaleTransaction(Sale sale, Payment payment, Car car) {
        paymentRepo.save(PaymentJpaEntity.fromDomain(payment));

        sale.assignPaymentId(payment.getId());
        saleRepo.save(SaleJpaEntity.fromDomain(sale));

        carRepo.save(com.fiap.carsales.infrastructure.persistence.entities.CarJpaEntity.fromDomain(car));
    }
}
