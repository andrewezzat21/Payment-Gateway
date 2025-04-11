package com.andrew.merchant_service.controller;

import com.andrew.merchant_service.dto.*;
import com.andrew.merchant_service.entity.Merchant;
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

    @GetMapping("/login")
    public ResponseEntity<ApiResponse<ApiKeyResponse>> merchantLogin(@RequestBody @Valid LoginDTO loginDTO){
        ApiKeyResponse response = merchantService.merchantLogin(loginDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Merchant profile!",
                        HttpStatus.OK.value(),
                        LocalDateTime.now(),
                        response));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<Merchant>> getProfile(@RequestHeader("X-API-KEY") String apiKey){
        Merchant merchant = merchantService.getProfile(apiKey);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Merchant api key found!",
                        HttpStatus.OK.value(),
                        LocalDateTime.now(),
                        merchant));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<MerchantResponse>> registerNewMerchant(@RequestBody @Valid MerchantDTO merchantDTO){
        MerchantResponse merchant = merchantService.registerNewMerchant(merchantDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Merchant registered successfully!",
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now(),
                        merchant));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<Merchant>> updateProfile(
            @RequestHeader("X-API-KEY") String apiKey,
            @RequestBody @Valid MerchantDTO merchantDTO
    ){
        Merchant merchant = merchantService.updateProfile(apiKey, merchantDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Merchant information updated successfully!",
                        HttpStatus.OK.value(),
                        LocalDateTime.now(),
                        merchant));
    }

}
