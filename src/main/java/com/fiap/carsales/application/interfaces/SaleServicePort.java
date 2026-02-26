package com.fiap.carsales.application.interfaces;

import com.fiap.carsales.application.dto.request.RegisterSaleRequest;
import com.fiap.carsales.application.dto.response.SaleResponse;

public interface SaleServicePort {
    SaleResponse execute(RegisterSaleRequest request);
}
