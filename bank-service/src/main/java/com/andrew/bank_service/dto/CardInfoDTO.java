package com.andrew.bank_service.dto;

import java.time.LocalDate;

public record CardInfoDTO(
        String cardNumber,
        String cardholderName,
        CardType cardType,
        LocalDate expiryDate
) {

}