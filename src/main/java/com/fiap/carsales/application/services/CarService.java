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
        return cars.stream().map(this::toResponse).toList();
    }

    @Override
    public List<CarResponse> getCarsSold() {
        var cars = repository.getByStatusOrderedByPriceAsc(CarStatus.SOLD);
        return cars.stream().map(this::toResponse).toList();
    }

    @Override
    public void registerCar(RegisterCarRequest request) {
        var existing = repository.getByLicensePlate(request.licensePlate());
        if (existing.isPresent()) {
            throw new BusinessException("Placa já registrada: " + request.licensePlate());
        }

        var car = Car.create(
                request.brand(),
                request.model(),
                request.year(),
                request.color(),
                request.licensePlate(),
                request.price()
        );
        repository.add(car);
    }

    @Override
    public CarResponse updateCar(UUID id, UpdateCarRequest request) {
        var car = repository.getById(id).orElseThrow(() -> new NotFoundException("Carro não encontrado."));
        car.updateDetails(
                request.brand(),
                request.model(),
                request.year(),
                request.color(),
                request.price()
        );
        repository.update(car);
        return toResponse(car);
    }

    private CarResponse toResponse(Car car) {
        return new CarResponse(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getColor(),
                car.getLicensePlate(),
                car.getPrice(),
                car.getStatus().name(),
                car.getUpdatedAt()
        );
    }
}
