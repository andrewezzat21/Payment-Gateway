package com.andrew.bank_service.dto;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.*;

public record AccountDTO(

        @NotNull(message = "Card number cannot be null")
        @Pattern(regexp = "^[0-9]{16}$", message = "Card number must be 16 digits")
        String cardNumber,

        @NotNull(message = "CVV cannot be null")
        @Pattern(regexp = "^[0-9]{3}$", message = "CVV must be 3 digits")
        String cvv,

        @NotNull(message = "Cardholder name cannot be null")
        @Size(min = 1, max = 100, message = "Cardholder name must be between 1 and 100 characters")
        String cardholderName
) {
}