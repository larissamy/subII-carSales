package com.fiap.carsales.infrastructure.persistence.entities;

import com.fiap.carsales.domain.entities.Car;
import com.fiap.carsales.domain.enums.CarStatus;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "TB_Cars")
public class CarJpaEntity {

    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(nullable = false, length = 100)
    private String brand;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false, length = 50)
    private String color;

    @Column(nullable = false, length = 20, unique = true)
    private String licensePlate;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CarStatus status;

    protected CarJpaEntity() {}

    public static CarJpaEntity fromDomain(Car car) {
        var e = new CarJpaEntity();
        e.id = car.getId().toString();
        e.brand = car.getBrand();
        e.model = car.getModel();
        e.year = car.getYear();
        e.color = car.getColor();
        e.licensePlate = car.getLicensePlate();
        e.price = car.getPrice();
        e.status = car.getStatus();
        return e;
    }

    public Car toDomain() {
        // Build a domain Car using the factory, then patch id/status (keeping domain free of JPA)
        Car domain = Car.create(brand, model, year, color, licensePlate, price);
        // Overwrite generated id/status by reconstructing via reflection would be ugly.
        // So we map by creating a new instance via a small internal constructor trick.
        return CarMapper.rehydrate(
                UUID.fromString(id),
                brand,
                model,
                year,
                color,
                licensePlate,
                price,
                status
        );
    }

    // Getters for Spring Data (optional)
    public String getId() { return id; }
    public CarStatus getStatus() { return status; }
}
