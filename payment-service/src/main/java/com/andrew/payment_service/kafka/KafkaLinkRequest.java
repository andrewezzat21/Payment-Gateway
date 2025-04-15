package com.andrew.payment_service.kafka;


import com.andrew.payment_service.dto.Currency;

public record KafkaLinkRequest(
        Long merchantId,
        String linkId,
        Double amount,
        Currency currency
) {
}
