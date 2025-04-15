package com.andrew.payment_service.kafka;


import com.andrew.payment_service.dto.CardInfo;

public record CardResponse(
        Long paymentId,
        CardInfo merchantCardInfo
) {
}
