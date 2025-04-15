package com.andrew.bank_service.kafka;


import com.andrew.bank_service.dto.CardInfo;

public record TransactionRequest(
        Long paymentId,
        CardInfo merchantCard,
        CardInfo customerCard,
        Double amount
) {
}
