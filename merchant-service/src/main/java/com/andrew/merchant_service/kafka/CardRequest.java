package com.andrew.merchant_service.kafka;

public record CardRequest(
        Long paymentId,
        Long merchantId
) {
}
