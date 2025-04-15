package com.andrew.merchant_service.dto;

public record PaymentLinkRequest(
        Double amount,
        Currency currency
) {
}
