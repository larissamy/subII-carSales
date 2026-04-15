package com.fiap.carsales.infrastructure.persistence.entities;

import com.fiap.carsales.domain.entities.Sale;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    @Column(nullable = false, length = 36)
    private String buyerId;

    @Column(nullable = false, length = 150)
    private String buyerEmail;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Instant createdAt;

    protected SaleJpaEntity() {
    }

    public static SaleJpaEntity fromDomain(Sale sale) {
        var e = new SaleJpaEntity();
        e.id = sale.getId().toString();
        e.carId = sale.getCarId().toString();
        e.paymentId = sale.getPaymentId() == null ? null : sale.getPaymentId().toString();
        e.buyerId = sale.getBuyerId().toString();
        e.buyerEmail = sale.getBuyerEmail();
        e.price = sale.getPrice();
        e.createdAt = sale.getCreatedAt();
        return e;
    }

    public Sale toDomain() {
        return SaleMapper.rehydrate(
                UUID.fromString(id),
                UUID.fromString(carId),
                paymentId == null ? null : UUID.fromString(paymentId),
                UUID.fromString(buyerId),
                buyerEmail,
                price,
                createdAt
        );
    }
}