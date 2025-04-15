package com.andrew.payment_service.controller;

import com.andrew.payment_service.dto.ApiResponse;
import com.andrew.payment_service.dto.PaymentRequestDTO;
import com.andrew.payment_service.entity.Payment;
import com.andrew.payment_service.service.PaymentsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentsController {

    private final PaymentsService paymentsService;

    @PostMapping("/{linkId}/pay")
    public ResponseEntity<ApiResponse<Payment>> createPaymentRequest(
            @PathVariable String linkId,
            @RequestBody @Valid PaymentRequestDTO paymentRequestDTO
    ){
        paymentsService.createPaymentRequest(linkId, paymentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Payment request is created! Check your email",
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now(),
                        null));
    }

}
