package com.fiap.carsales.application.services;

import com.fiap.carsales.application.dto.request.RegisterSaleRequest;
import com.fiap.carsales.application.dto.response.SaleResponse;
import com.fiap.carsales.application.exceptions.BusinessException;
import com.fiap.carsales.application.exceptions.NotFoundException;
import com.fiap.carsales.application.interfaces.SaleServicePort;
import com.fiap.carsales.domain.entities.Payment;
import com.fiap.carsales.domain.entities.Sale;
import com.fiap.carsales.domain.enums.CarStatus;
import com.fiap.carsales.domain.repositories.CarRepository;
import com.fiap.carsales.domain.repositories.SaleRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterSaleService implements SaleServicePort {

    private final CarRepository carRepository;
    private final SaleRepository saleRepository;

    public RegisterSaleService(CarRepository carRepository, SaleRepository saleRepository) {
        this.carRepository = carRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    public SaleResponse execute(RegisterSaleRequest request) {
        var car = carRepository.getById(request.carId())
                .orElseThrow(() -> new NotFoundException("Carro não encontrado."));

        if (car.getStatus() != CarStatus.AVAILABLE) {
            throw new BusinessException("The car " + car.getLicensePlate() + " is not available for sale. Current status: " + car.getStatus());
        }

        // Reserve until payment confirmation
        car.reserve();

        var payment = Payment.create(car.getPrice());
        var sale = Sale.create(car.getId(), car.getPrice(), request.taxId(), request.saleDate());

        saleRepository.createSaleTransaction(sale, payment, car);

        return new SaleResponse(
                sale.getId(),
                sale.getCarId(),
                sale.getPrice(),
                payment.getPaymentCode(),
                sale.getCreatedAt(),
                payment.getPaymentStatus().name()
        );
    }
}
