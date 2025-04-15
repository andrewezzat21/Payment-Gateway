package com.andrew.payment_service.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PaymentRequestDTO(
        @NotBlank(message = "Email is required")
        @Email(message = "Email is not valid!")
        String customerEmail,

        @NotBlank(message = "Card number is required")
        @Size(min = 16, max = 16, message = "Card number must be 16 digits")
        String cardNumber,

        @NotBlank(message = "Cardholder name is required")
        @Size(max = 100, message = "Cardholder name must be at most 100 characters")
        String cardholderName,

        @NotNull(message = "Card type is required")
        @Enumerated(EnumType.STRING)
        CardType cardType,

        @NotNull(message = "Expiry date is required")
        @Future(message = "Expiry date must be in the future")
        LocalDate expiryDate
) {
}
