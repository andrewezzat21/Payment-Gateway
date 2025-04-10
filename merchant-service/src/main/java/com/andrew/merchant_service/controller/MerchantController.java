package com.andrew.merchant_service.controller;

import com.andrew.merchant_service.dto.ApiResponse;
import com.andrew.merchant_service.dto.MerchantDTO;
import com.andrew.merchant_service.dto.MerchantResponse;
import com.andrew.merchant_service.service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchants")
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping
    public String helloWorld(){
        return "HelloWorld";
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MerchantResponse>> registerNewMerchant(@RequestBody @Valid MerchantDTO merchantDTO){
        MerchantResponse merchant = merchantService.registerNewMerchant(merchantDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Merchant registered successfully!",
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now(),
                        merchant));
    }

}
