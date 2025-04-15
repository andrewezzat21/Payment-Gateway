package com.andrew.merchant_service.dto;

import java.time.LocalDate;

public record CardInfo(
        String cardNumber,
        String cardholderName,
        CardType cardType,
        LocalDate expiryDate
) {
}
