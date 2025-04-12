package com.andrew.bank_service.kafka;

import com.andrew.bank_service.dto.CardInfoDTO;

public record CardValidationResponse(
        String requestId,
        CardInfoDTO cardInfoDTO,
        String status
) {
}
