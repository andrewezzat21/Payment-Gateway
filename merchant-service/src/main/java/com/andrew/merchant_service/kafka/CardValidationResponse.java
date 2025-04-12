package com.andrew.merchant_service.kafka;

import com.andrew.merchant_service.dto.CardInfoDTO;

public record CardValidationResponse(
        String requestId,
        CardInfoDTO cardInfoDTO,
        String status
) {
}
