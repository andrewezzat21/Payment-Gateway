package com.andrew.bank_service.service;

import com.andrew.bank_service.dto.CardInfoDTO;
import com.andrew.bank_service.entity.Account;
import com.andrew.bank_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankService {

    private final AccountRepository accountRepository;

    public boolean isValidCard(CardInfoDTO cardInfoDTO){
        Optional<Account> account = accountRepository.findByCardNumber(cardInfoDTO.cardNumber());
        return account.isPresent()
                && account.get().getCardholderName().equals(cardInfoDTO.cardholderName())
                && account.get().getCardType().equals(cardInfoDTO.cardType())
                && account.get().getExpiryDate().equals(cardInfoDTO.expiryDate());
    }

}
