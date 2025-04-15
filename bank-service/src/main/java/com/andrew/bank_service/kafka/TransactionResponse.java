package com.andrew.bank_service.kafka;


import com.andrew.bank_service.dto.Status;

public record TransactionResponse(
        Long paymentId,
        Status status,
        String message
) {
}
