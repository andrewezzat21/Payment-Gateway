package com.andrew.bank_service.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CardInfoDTO(
        @NotBlank(message = "Card number is required")
        @Size(min = 16, max = 16, message = "Card number must be 16 digits")
        String cardNumber,

        @NotNull(message = "Card type is required")
        @Enumerated(EnumType.STRING)
        CardType cardType,

        @NotBlank(message = "Cardholder name is required")
        @Size(max = 100, message = "Cardholder name must be at most 100 characters")
        String cardholderName,

        @NotNull(message = "Expiry date is required")
        @Future(message = "Expiry date must be in the future")
        LocalDate expiryDate
) {

}