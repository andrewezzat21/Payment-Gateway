package com.andrew.bank_service.service;

import com.andrew.bank_service.dto.CardInfoDTO;
import com.andrew.bank_service.dto.Status;
import com.andrew.bank_service.entity.Account;
import com.andrew.bank_service.entity.Transaction;
import com.andrew.bank_service.kafka.KafkaProducer;
import com.andrew.bank_service.kafka.TransactionRequest;
import com.andrew.bank_service.kafka.TransactionResponse;
import com.andrew.bank_service.repository.AccountRepository;
import com.andrew.bank_service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final EncryptionService encryptionService;

    private final KafkaProducer kafkaProducer;

    public boolean isValidCard(CardInfoDTO cardInfoDTO){
        Optional<Account> account = accountRepository.findByCardNumber(cardInfoDTO.cardNumber());
        return account.isPresent()
                && account.get().getCardholderName().equals(cardInfoDTO.cardholderName())
                && account.get().getCardType().equals(cardInfoDTO.cardType())
                && account.get().getExpiryDate().equals(cardInfoDTO.expiryDate());
    }

    @Transactional
    public void makeTransaction(TransactionRequest transactionRequest) {

        // Validate the customer card
        if (!isValidCard(new CardInfoDTO(
                encryptionService.encrypt(transactionRequest.customerCard().cardNumber()),
                transactionRequest.customerCard().cardType(),
                encryptionService.encrypt(transactionRequest.customerCard().cardholderName()),
                transactionRequest.customerCard().expiryDate()
        ))) {
            kafkaProducer.sendTransactionResponse(
                    new TransactionResponse(
                            transactionRequest.paymentId(),
                            Status.FAILED,
                            "Card is not valid!"
                    )
            );
            return;
        }

        // Check if sender account exists
        Optional<Account> senderOpt = accountRepository.findByCardNumber(
                encryptionService.encrypt(transactionRequest.customerCard().cardNumber())
        );

        if (senderOpt.isEmpty()) {
            kafkaProducer.sendTransactionResponse(
                    new TransactionResponse(
                            transactionRequest.paymentId(),
                            Status.FAILED,
                            "Sender account not found!"
                    )
            );
            return;
        }

        Account senderAccount = senderOpt.get();

        // Check balance
        if (transactionRequest.amount() > senderAccount.getBalance()) {
            kafkaProducer.sendTransactionResponse(
                    new TransactionResponse(
                            transactionRequest.paymentId(),
                            Status.FAILED,
                            "Not enough balance"
                    )
            );
            return;
        }

        // Check if receiver account exists
        Optional<Account> receiverOpt = accountRepository.findByCardNumber(
                transactionRequest.merchantCard().cardNumber()
        );

        if (receiverOpt.isEmpty()) {
            kafkaProducer.sendTransactionResponse(
                    new TransactionResponse(
                            transactionRequest.paymentId(),
                            Status.FAILED,
                            "Receiver account not found!"
                    )
            );
            return;
        }

        Account receiverAccount = receiverOpt.get();

        // Save transaction
        Transaction transaction = Transaction.builder()
                .amount(transactionRequest.amount())
                .senderAccount(senderAccount)
                .receiverAccount(receiverAccount)
                .build();

        transactionRepository.save(transaction);

        // Update balances
        updateAccountBalance(senderAccount, -transactionRequest.amount());
        updateAccountBalance(receiverAccount, transactionRequest.amount());

        // Send success response after everything is done
        kafkaProducer.sendTransactionResponse(
                new TransactionResponse(
                        transactionRequest.paymentId(),
                        Status.SUCCESS,
                        "Transaction made successfully"
                )
        );
    }

    @Transactional
    void updateAccountBalance(Account account, Double amount) {
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

}
