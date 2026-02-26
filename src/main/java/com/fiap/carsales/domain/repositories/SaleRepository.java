package com.fiap.carsales.domain.repositories;

import com.fiap.carsales.domain.entities.Car;
import com.fiap.carsales.domain.entities.Payment;
import com.fiap.carsales.domain.entities.Sale;

import java.util.Optional;
import java.util.UUID;

public interface SaleRepository {
    void add(Sale sale);
    void update(Sale sale);
    Optional<Sale> getById(UUID id);
    Optional<Sale> getByPaymentId(UUID paymentId);
    void createSaleTransaction(Sale sale, Payment payment, Car car);
}
