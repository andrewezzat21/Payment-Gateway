package com.andrew.merchant_service.kafka;

public record KafkaLinkResponse(
        Long merchantId,
        String linkId
) {
}
