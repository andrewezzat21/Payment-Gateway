package com.andrew.merchant_service.kafka;

import com.andrew.merchant_service.dto.CardInfoDTO;

public record CardValidationRequest(
        String requestId,
        CardInfoDTO cardInfoDTO
) {
}
