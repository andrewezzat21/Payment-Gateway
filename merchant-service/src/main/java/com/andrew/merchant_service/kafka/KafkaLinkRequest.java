package com.andrew.merchant_service.kafka;

import com.andrew.merchant_service.dto.Currency;

public record KafkaLinkRequest(
        Long merchantId,
        String linkId,
        Double amount,
        Currency currency
) {
}
