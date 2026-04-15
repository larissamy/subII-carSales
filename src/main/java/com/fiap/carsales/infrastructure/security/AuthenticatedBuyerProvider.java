package com.fiap.carsales.infrastructure.security;

import com.fiap.carsales.application.exceptions.BusinessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedBuyerProvider {

    public AuthenticatedBuyer getRequiredBuyer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedBuyer buyer)) {
            throw new BusinessException("Comprador autenticado não encontrado.");
        }

        return buyer;
    }
}