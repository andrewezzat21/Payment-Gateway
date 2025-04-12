package com.andrew.bank_service.controller;

import com.andrew.bank_service.entity.Account;
import com.andrew.bank_service.repository.AccountRepository;
import com.andrew.bank_service.service.EncryptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bank")
public class BankController {

    private final AccountRepository accountRepository;
    private final EncryptionService encryptionService;

    @PostMapping("/test")
    public void test(
            @RequestBody @Valid Account accountDTO
    ){
       Account account = Account.builder()
               .cardNumber(encryptionService.encrypt(accountDTO.getCardNumber()))
               .cardholderName(encryptionService.encrypt(accountDTO.getCardholderName()))
               .expiryDate(accountDTO.getExpiryDate())
               .cardType(accountDTO.getCardType())
               .balance(accountDTO.getBalance())
               .build();
       accountRepository.save(account);
    }


}
