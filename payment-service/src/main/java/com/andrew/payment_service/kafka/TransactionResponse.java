package com.andrew.payment_service.kafka;

import com.andrew.payment_service.dto.Status;

public record TransactionResponse(
        Long paymentId,
        Status status,
        String message
) {
}
