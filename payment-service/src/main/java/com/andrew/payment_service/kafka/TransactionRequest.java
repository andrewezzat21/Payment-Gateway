package com.andrew.payment_service.kafka;

import com.andrew.payment_service.dto.CardInfo;

public record TransactionRequest(
        Long paymentId,
        CardInfo merchantCard,
        CardInfo customerCard,
        Double amount
) {
}
