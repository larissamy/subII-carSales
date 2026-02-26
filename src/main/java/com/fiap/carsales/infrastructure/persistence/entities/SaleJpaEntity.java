package com.fiap.carsales.infrastructure.persistence.entities;

import com.fiap.carsales.domain.entities.Sale;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "TB_Sales")
public class SaleJpaEntity {

    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(nullable = false, length = 36)
    private String carId;

    @Column(length = 36)
    private String paymentId;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, length = 30)
    private String taxId;

    @Column(nullable = false)
    private Instant createdAt;

    protected SaleJpaEntity() {}

    public static SaleJpaEntity fromDomain(Sale sale) {
        var e = new SaleJpaEntity();
        e.id = sale.getId().toString();
        e.carId = sale.getCarId().toString();
        e.paymentId = sale.getPaymentId() == null ? null : sale.getPaymentId().toString();
        e.price = sale.getPrice();
        e.taxId = sale.getTaxId();
        e.createdAt = sale.getCreatedAt();
        return e;
    }

    public Sale toDomain() {
        return SaleMapper.rehydrate(
                UUID.fromString(id),
                UUID.fromString(carId),
                paymentId == null ? null : UUID.fromString(paymentId),
                price,
                taxId,
                createdAt
        );
    }
}
