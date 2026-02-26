package com.fiap.carsales.domain.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Sale {
    private final UUID id;
    private final UUID carId;
    private UUID paymentId; // assigned when persisting transaction
    private final BigDecimal price;
    private final String taxId;
    private final Instant createdAt;

    private Sale(UUID id, UUID carId, UUID paymentId, BigDecimal price, String taxId, Instant createdAt) {
        this.id = Objects.requireNonNull(id);
        this.carId = Objects.requireNonNull(carId);
        this.paymentId = paymentId;
        this.price = Objects.requireNonNull(price);
        this.taxId = Objects.requireNonNull(taxId);
        this.createdAt = Objects.requireNonNull(createdAt);
    }

    public static Sale create(UUID carId, BigDecimal price, String taxId, Instant saleDate) {
        Instant ts = saleDate == null ? Instant.now() : saleDate;
        return new Sale(UUID.randomUUID(), carId, null, price, taxId, ts);
    }

    public UUID getId() { return id; }
    public UUID getCarId() { return carId; }
    public UUID getPaymentId() { return paymentId; }
    public BigDecimal getPrice() { return price; }
    public String getTaxId() { return taxId; }
    public Instant getCreatedAt() { return createdAt; }

    // Used by persistence adapter
    public void assignPaymentId(UUID paymentId) { this.paymentId = paymentId; }
}
