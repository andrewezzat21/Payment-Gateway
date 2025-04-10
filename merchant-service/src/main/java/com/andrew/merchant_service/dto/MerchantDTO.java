package com.andrew.merchant_service.dto;

import com.andrew.merchant_service.entity.CardType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record MerchantDTO(
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,

        @NotBlank(message = "First name is required")
        @Size(min = 2, message = "First name must be at least 2 characters long")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(min = 2, message = "Last name must be at least 2 characters long")
        String lastName,

        @NotBlank(message = "Phone number is required")
        String phoneNumber,

        @NotBlank(message = "Card number is required")
        @Size(min = 16, max = 16, message = "Card number must be 16 digits")
        String cardNumber,

        @NotBlank(message = "CVV is required")
        @Pattern(regexp = "\\d{3,4}", message = "CVV must be 3 or 4 digits")
        String cvv,

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
