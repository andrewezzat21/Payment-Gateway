package com.andrew.payment_service.kafka;

public record CardRequest(
        Long paymentId,
        Long merchantId
) {
}
