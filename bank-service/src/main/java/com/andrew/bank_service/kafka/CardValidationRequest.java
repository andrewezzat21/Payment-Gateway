package com.andrew.bank_service.kafka;

import com.andrew.bank_service.dto.CardInfoDTO;

public record CardValidationRequest(
        String requestId,
        CardInfoDTO cardInfoDTO
) {
}
