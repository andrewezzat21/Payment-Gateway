package com.andrew.merchant_service.kafka;

import com.andrew.merchant_service.dto.CardInfo;

public record CardResponse(
        Long paymentId,
        CardInfo merchantCardInfo
) {
}
