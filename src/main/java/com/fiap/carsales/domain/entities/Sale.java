package com.fiap.carsales.domain.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Sale {
    private final UUID id;
    private final UUID carId;
    private UUID paymentId;
    private final UUID buyerId;
    private final String buyerEmail;
    private final BigDecimal price;
    private final Instant createdAt;

    private Sale(UUID id,
                 UUID carId,
                 UUID paymentId,
                 UUID buyerId,
                 String buyerEmail,
                 BigDecimal price,
                 Instant createdAt) {
        this.id = Objects.requireNonNull(id);
        this.carId = Objects.requireNonNull(carId);
        this.paymentId = paymentId;
        this.buyerId = Objects.requireNonNull(buyerId);
        this.buyerEmail = Objects.requireNonNull(buyerEmail);
        this.price = Objects.requireNonNull(price);
        this.createdAt = Objects.requireNonNull(createdAt);
    }

    public static Sale create(UUID carId,
                              UUID buyerId,
                              String buyerEmail,
                              BigDecimal price,
                              Instant saleDate) {
        Instant ts = saleDate == null ? Instant.now() : saleDate;
        return new Sale(UUID.randomUUID(), carId, null, buyerId, buyerEmail, price, ts);
    }

    public UUID getId() {
        return id;
    }

    public UUID getCarId() {
        return carId;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public UUID getBuyerId() {
        return buyerId;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void assignPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }
}