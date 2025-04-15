package com.andrew.payment_service.kafka;

public record KafkaLinkResponse(
        Long merchantId,
        String linkId
) {
}
