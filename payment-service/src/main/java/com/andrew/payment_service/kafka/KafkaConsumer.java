package com.andrew.payment_service.kafka;

import com.andrew.payment_service.service.PaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final PaymentsService paymentsService;

    @KafkaListener(topics = "link-request", groupId = "paymentGateway")
    public void consumeLinkRequest(@Payload KafkaLinkRequest kafkaLinkRequest){
        paymentsService.createPaymentLink(kafkaLinkRequest);
    }

    @KafkaListener(topics = "merchant-card-response", groupId = "paymentGateway")
    public void consumeCardResponse(@Payload CardResponse cardResponse){
        paymentsService.createTransactionRequest(cardResponse);
    }

    @KafkaListener(topics = "transaction-response", groupId = "paymentGateway")
    public void consumeTransactionResponse(@Payload TransactionResponse transactionResponse){
        paymentsService.updatePaymentStatus(transactionResponse);
    }

}
