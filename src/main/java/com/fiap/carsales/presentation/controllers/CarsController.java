package com.fiap.carsales.presentation.controllers;

import com.fiap.carsales.application.dto.request.RegisterCarRequest;
import com.fiap.carsales.application.dto.request.UpdateCarRequest;
import com.fiap.carsales.application.interfaces.CarServicePort;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cars")
public class CarsController {

    private final CarServicePort service;

    public CarsController(CarServicePort service) {
        this.service = service;
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAllAvailable() {
        return ResponseEntity.ok(service.getCarsSale());
    }

    @GetMapping("/sold")
    public ResponseEntity<?> getAllSold() {
        return ResponseEntity.ok(service.getCarsSold());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RegisterCarRequest request) {
        service.registerCar(request);
        return ResponseEntity.ok("Car successfully registered");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody UpdateCarRequest request) {
        return ResponseEntity.ok(service.updateCar(id, request));
    }
}
