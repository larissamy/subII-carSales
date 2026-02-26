package com.fiap.carsales.domain.entities;

import com.fiap.carsales.domain.enums.CarStatus;

import java.math.BigDecimal;
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

    private Car(UUID id, String brand, String model, int year, String color, String licensePlate, BigDecimal price, CarStatus status) {
        this.id = Objects.requireNonNull(id);
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.licensePlate = licensePlate;
        this.price = price;
        this.status = status;
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
                CarStatus.AVAILABLE
        );
    }

    public void updateDetails(String brand, String model, int year, String color, BigDecimal price) {
        if (status == CarStatus.SOLD) {
            throw new IllegalStateException("Carro vendido, impossivel de editar");
        }
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.price = price;
    }

    public void makeAvailable() {
        if (status == CarStatus.AVAILABLE) {
            throw new IllegalStateException("The car is now available.");
        }
        if (status == CarStatus.SOLD) {
            throw new IllegalStateException("Car sold.");
        }
        this.status = CarStatus.AVAILABLE;
    }

    public void markAsSold() {
        if (status == CarStatus.SOLD) {
            throw new IllegalStateException("This car has already been sold.");
        }
        if (status != CarStatus.AVAILABLE) {
            throw new IllegalStateException("Only available cars can be sold.");
        }
        this.status = CarStatus.SOLD;
    }

    public UUID getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getColor() { return color; }
    public String getLicensePlate() { return licensePlate; }
    public BigDecimal getPrice() { return price; }
    public CarStatus getStatus() { return status; }

    // Used by persistence adapter
    void setStatus(CarStatus status) { this.status = status; }
}
