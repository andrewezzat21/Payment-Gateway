package com.andrew.bank_service.service;

import com.andrew.bank_service.dto.AccountDTO;
import com.andrew.bank_service.entity.Account;
import com.andrew.bank_service.exception.AccountNotFoundException;
import com.andrew.bank_service.exception.InvalidAccountInformationException;
import com.andrew.bank_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public ResponseEntity<String> validateAccount(AccountDTO accountDTO){
        Account repoAccount = accountRepository.findByCardNumber(accountDTO.cardNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account not found with card number: "+ accountDTO.cardNumber()));

        if (!accountDTO.cvv().equals(repoAccount.getCvv()) || !accountDTO.cardholderName().equals(repoAccount.getCardholderName())) {
            throw new InvalidAccountInformationException("Account information is incorrect");
        }

        return ResponseEntity.ok("Valid Card!");
    }

}
