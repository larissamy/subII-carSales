package com.fiap.carsales.application.interfaces;

import com.fiap.carsales.application.dto.request.PaymentWebhookRequest;

public interface PaymentConfirmationPort {
    void confirm(PaymentWebhookRequest request);
}
