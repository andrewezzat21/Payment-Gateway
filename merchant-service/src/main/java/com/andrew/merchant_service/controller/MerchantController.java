package com.andrew.merchant_service.controller;

import com.andrew.merchant_service.dto.*;
import com.andrew.merchant_service.entity.Merchant;
import com.andrew.merchant_service.kafka.KafkaProducer;
import com.andrew.merchant_service.service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchants")
public class MerchantController {

    private final MerchantService merchantService;
    private final KafkaProducer kafkaProducer;

    @PostMapping("/login")
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

    @PostMapping("/payment-link")
    public ResponseEntity<ApiResponse<PaymentLinkResponse>> createPaymentLink(
            @RequestHeader("X-API-KEY") String apiKey,
            @RequestBody @Valid PaymentLinkRequest paymentLinkRequest

    ){
        PaymentLinkResponse paymentLinkResponse = merchantService.createPaymentLink(apiKey, paymentLinkRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new ApiResponse<>("Payment link is being created!",
                        HttpStatus.ACCEPTED.value(),
                        LocalDateTime.now(),
                        paymentLinkResponse));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<MerchantResponse>> updateProfile(
            @RequestHeader("X-API-KEY") String apiKey,
            @RequestBody @Valid MerchantDTO merchantDTO
    ){
        MerchantResponse merchantResponse = merchantService.updateProfile(apiKey, merchantDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Merchant information updated successfully!",
                        HttpStatus.OK.value(),
                        LocalDateTime.now(),
                        merchantResponse));
    }

    @PutMapping("/card")
    public ResponseEntity<ApiResponse<MerchantResponse>> updateCardInfo(
            @RequestHeader("X-API-KEY") String apiKey,
            @RequestBody @Valid CardInfoDTO cardInfoDTO
    ){
        merchantService.validateCardInfo(apiKey, cardInfoDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new ApiResponse<>("Merchant card information being validated! Check email",
                        HttpStatus.ACCEPTED.value(),
                        LocalDateTime.now(),
                        null));
    }

//    @PostMapping("/test")
//    public void test(){
//        CardInfoDTO cardInfoDTO = new CardInfoDTO("1234567891234567",
//                CardType.VISA,
//                "Andrew Ezzat",
//                LocalDate.of(2027, 6, 30));
//        kafkaProducer.sendMessage(cardInfoDTO);
//    }


}
