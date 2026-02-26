package com.fiap.carsales.application.interfaces;

import com.fiap.carsales.application.dto.response.PaymentResponse;

import java.util.Optional;

public interface PaymentServicePort {
    Optional<PaymentResponse> getPaymentByPaymentCode(String paymentCode);
}
