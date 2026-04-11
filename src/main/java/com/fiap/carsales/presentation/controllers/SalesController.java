package com.fiap.carsales.presentation.controllers;

import com.fiap.carsales.application.dto.request.RegisterSaleRequest;
import com.fiap.carsales.application.dto.response.SaleResponse;
import com.fiap.carsales.application.interfaces.SaleServicePort;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    private final SaleServicePort service;

    public SalesController(SaleServicePort service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SaleResponse> create(@Valid @RequestBody RegisterSaleRequest request) {
        SaleResponse saleResponse = service.execute(request);
        return ResponseEntity
                .created(URI.create("/api/sales/" + saleResponse.id()))
                .body(saleResponse);
    }
}