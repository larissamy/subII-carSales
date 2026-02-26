package com.fiap.carsales.application.services;

import com.fiap.carsales.application.dto.request.PaymentWebhookRequest;
import com.fiap.carsales.application.dto.response.PaymentResponse;
import com.fiap.carsales.application.exceptions.NotFoundException;
import com.fiap.carsales.application.interfaces.PaymentConfirmationPort;
import com.fiap.carsales.application.interfaces.PaymentServicePort;
import com.fiap.carsales.domain.enums.CarStatus;
import com.fiap.carsales.domain.enums.PaymentStatus;
import com.fiap.carsales.domain.repositories.CarRepository;
import com.fiap.carsales.domain.repositories.PaymentRepository;
import com.fiap.carsales.domain.repositories.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService implements PaymentServicePort, PaymentConfirmationPort {

    private final PaymentRepository paymentRepository;
    private final SaleRepository saleRepository;
    private final CarRepository carRepository;

    public PaymentService(PaymentRepository paymentRepository, SaleRepository saleRepository, CarRepository carRepository) {
        this.paymentRepository = paymentRepository;
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
    }

    /**
     * Webhook endpoint:
     * status: 0 = PENDING, 1 = PAID, 2 = CANCELLED
     */
    @Override
    public void confirm(PaymentWebhookRequest input) {
        if (input.status() == null || input.status() < 0 || input.status() > 2) {
            throw new IllegalArgumentException("Invalid payment status (expected 0, 1 or 2)");
        }

        var payment = paymentRepository.getByPaymentCode(input.paymentCode())
                .orElseThrow(() -> new NotFoundException("Payment with code " + input.paymentCode() + " not found"));

        PaymentStatus incoming = switch (input.status()) {
            case 0 -> PaymentStatus.PENDING;
            case 1 -> PaymentStatus.PAID;
            case 2 -> PaymentStatus.CANCELLED;
            default -> throw new IllegalStateException("Unexpected value: " + input.status());
        };

        // Update payment state
        if (incoming == PaymentStatus.PAID) {
            payment.confirmPayment();
        } else if (incoming == PaymentStatus.CANCELLED) {
            payment.cancelPayment();
        } // PENDING: no-op

        paymentRepository.update(payment);

        // Update car state accordingly (if we can resolve the sale)
        var sale = saleRepository.getByPaymentId(payment.getId())
                .orElseThrow(() -> new NotFoundException("Sale for payment " + payment.getPaymentCode() + " not found"));

        var car = carRepository.getById(sale.getCarId())
                .orElseThrow(() -> new NotFoundException("Car not found for sale " + sale.getId()));

        if (incoming == PaymentStatus.PAID) {
            // RESERVED -> SOLD
            if (car.getStatus() != CarStatus.SOLD) {
                car.markAsSold();
                carRepository.update(car);
            }
        } else if (incoming == PaymentStatus.CANCELLED) {
            // RESERVED -> AVAILABLE
            if (car.getStatus() == CarStatus.RESERVED) {
                car.makeAvailable();
                carRepository.update(car);
            }
        }
    }

    @Override
    public Optional<PaymentResponse> getPaymentByPaymentCode(String paymentCode) {
        return paymentRepository.getByPaymentCode(paymentCode)
                .map(p -> new PaymentResponse(
                        p.getId(),
                        p.getAmount(),
                        p.getPaymentCode(),
                        p.getPaymentStatus()
                ));
    }
}
