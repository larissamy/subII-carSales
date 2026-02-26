package com.fiap.carsales.application.services;

import com.fiap.carsales.application.dto.request.PaymentWebhookRequest;
import com.fiap.carsales.application.dto.response.PaymentResponse;
import com.fiap.carsales.application.exceptions.NotFoundException;
import com.fiap.carsales.application.interfaces.PaymentConfirmationPort;
import com.fiap.carsales.application.interfaces.PaymentServicePort;
import com.fiap.carsales.domain.enums.PaymentStatus;
import com.fiap.carsales.domain.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService implements PaymentServicePort, PaymentConfirmationPort {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void confirm(PaymentWebhookRequest input) {
        if (input.status() == null || input.status() < 0 || input.status() > PaymentStatus.values().length - 1) {
            throw new IllegalArgumentException("Invalid payment status");
        }

        var payment = paymentRepository.getByPaymentCode(input.paymentCode())
                .orElseThrow(() -> new NotFoundException("Payment with code " + input.paymentCode() + " not found"));

        // Same behavior as .NET: always confirm payment (ignores incoming status)
        payment.confirmPayment();
        paymentRepository.update(payment);
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
