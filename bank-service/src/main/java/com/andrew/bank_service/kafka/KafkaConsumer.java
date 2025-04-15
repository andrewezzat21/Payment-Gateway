package com.andrew.bank_service.kafka;

import com.andrew.bank_service.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final BankService bankService;
    private final KafkaProducer kafkaProducer;

    @KafkaListener(topics = "bank-card-validation", groupId = "paymentGateway")
    public void consumeCardFromMerchant(@Payload CardValidationRequest cardValidationRequest){
        CardValidationResponse response = new CardValidationResponse(cardValidationRequest.requestId(),
                cardValidationRequest.cardInfoDTO(),
                "false");

        if (bankService.isValidCard(cardValidationRequest.cardInfoDTO())) {
            response = new CardValidationResponse(cardValidationRequest.requestId(),
                    cardValidationRequest.cardInfoDTO(),
                    "true");
        }
        kafkaProducer.sendCardResponse(response);
    }

    @KafkaListener(topics = "transaction-request", groupId = "paymentGateway")
    public void consumeTransactionRequest(@Payload TransactionRequest transactionRequest){
        System.out.println(transactionRequest.toString());
        bankService.makeTransaction(transactionRequest);
    }

}
