package com.fiap.carsales.domain.entities;

import com.fiap.carsales.domain.enums.CarStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Car {
    private final UUID id;
    private String brand;
    private String model;
    private int year;
    private String color;
    private final String licensePlate;
    private BigDecimal price;
    private CarStatus status;
    private Instant updatedAt;

    private Car(UUID id, String brand, String model, int year, String color, String licensePlate, BigDecimal price, CarStatus status, Instant updatedAt) {
        this.id = Objects.requireNonNull(id);
        this.brand = Objects.requireNonNull(brand);
        this.model = Objects.requireNonNull(model);
        this.year = year;
        this.color = Objects.requireNonNull(color);
        this.licensePlate = Objects.requireNonNull(licensePlate);
        this.price = Objects.requireNonNull(price);
        this.status = Objects.requireNonNull(status);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    public static Car create(String brand, String model, int year, String color, String licensePlate, BigDecimal price) {
        return new Car(
                UUID.randomUUID(),
                brand,
                model,
                year,
                color,
                licensePlate,
                price,
                CarStatus.AVAILABLE,
                Instant.now()
        );
    }

    public void updateDetails(String brand, String model, int year, String color, BigDecimal price) {
        if (status == CarStatus.SOLD) {
            throw new IllegalStateException("Carro vendido, impossível de editar");
        }
        this.brand = Objects.requireNonNull(brand);
        this.model = Objects.requireNonNull(model);
        this.year = year;
        this.color = Objects.requireNonNull(color);
        this.price = Objects.requireNonNull(price);
        this.updatedAt = Instant.now();
    }

    public void reserve() {
        if (status != CarStatus.AVAILABLE) {
            throw new IllegalStateException("Only available cars can be reserved. Current status: " + status);
        }
        this.status = CarStatus.RESERVED;
        this.updatedAt = Instant.now();
    }

    public void makeAvailable() {
        if (status == CarStatus.AVAILABLE) {
            throw new IllegalStateException("The car is already available.");
        }
        if (status == CarStatus.SOLD) {
            throw new IllegalStateException("Car sold cannot become available.");
        }
        // from RESERVED -> AVAILABLE
        this.status = CarStatus.AVAILABLE;
        this.updatedAt = Instant.now();
    }

    public void markAsSold() {
        if (status == CarStatus.SOLD) {
            throw new IllegalStateException("This car has already been sold.");
        }
        if (status != CarStatus.RESERVED && status != CarStatus.AVAILABLE) {
            throw new IllegalStateException("Only reserved/available cars can be sold. Current status: " + status);
        }
        this.status = CarStatus.SOLD;
        this.updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getColor() { return color; }
    public String getLicensePlate() { return licensePlate; }
    public BigDecimal getPrice() { return price; }
    public CarStatus getStatus() { return status; }
    public Instant getUpdatedAt() { return updatedAt; }

    // Used by persistence adapter
    void setStatus(CarStatus status) { this.status = status; }
    void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
