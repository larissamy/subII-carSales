package com.fiap.carsales.application.services;

import com.fiap.carsales.application.dto.request.RegisterCarRequest;
import com.fiap.carsales.application.dto.request.UpdateCarRequest;
import com.fiap.carsales.application.dto.response.CarResponse;
import com.fiap.carsales.application.exceptions.BusinessException;
import com.fiap.carsales.application.exceptions.NotFoundException;
import com.fiap.carsales.application.interfaces.CarServicePort;
import com.fiap.carsales.domain.entities.Car;
import com.fiap.carsales.domain.enums.CarStatus;
import com.fiap.carsales.domain.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class CarService implements CarServicePort {

    private final CarRepository repository;

    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CarResponse> getCarsSale() {
        var cars = repository.getByStatusOrderedByPriceAsc(CarStatus.AVAILABLE);
        return cars.stream()
                .map(car -> new CarResponse(
                        car.getId(),
                        car.getLicensePlate(),
                        car.getModel(),
                        car.getYear(),
                        car.getPrice(),
                        null
                ))
                .toList();
    }

    @Override
    public List<CarResponse> getCarsSold() {
        var cars = repository.getByStatusOrderedByPriceAsc(CarStatus.SOLD);
        return cars.stream()
                .map(car -> new CarResponse(
                        car.getId(),
                        car.getLicensePlate(),
                        car.getModel(),
                        car.getYear(),
                        car.getPrice(),
                        null
                ))
                .toList();
    }

    @Override
    public void registerCar(RegisterCarRequest car) {
        // Validation annotations already run in controller; keep business rules here too.
        if (repository.getByLicensePlate(car.licensePlate()).isPresent()) {
            throw new BusinessException("This car is already registered");
        }

        var newCar = Car.create(
                car.brand(),
                car.model(),
                car.year(),
                car.color(),
                car.licensePlate(),
                car.price()
        );

        repository.add(newCar);
    }

    @Override
    public CarResponse updateCar(UUID id, UpdateCarRequest car) {
        var existingCar = repository.getById(id)
                .orElseThrow(() -> new NotFoundException("Car not found"));

        existingCar.updateDetails(
                car.brand(),
                car.model(),
                car.year(),
                car.color(),
                car.price()
        );

        repository.update(existingCar);

        return new CarResponse(
                existingCar.getId(),
                existingCar.getLicensePlate(),
                existingCar.getModel(),
                existingCar.getYear(),
                existingCar.getPrice(),
                Instant.now()
        );
    }
}
