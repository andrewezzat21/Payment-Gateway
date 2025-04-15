package com.andrew.merchant_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, CardValidationRequest> kafkaCardTemplate;
    private final KafkaTemplate<String, KafkaLinkRequest> kafkaLinkTemplate;
    private final KafkaTemplate<String, CardResponse> kafkaCardResponseTemplate;


    public void createPaymentLink(KafkaLinkRequest kafkaLinkRequest) {
        Message<KafkaLinkRequest> message = MessageBuilder
                .withPayload(kafkaLinkRequest)
                .setHeader(KafkaHeaders.TOPIC, "link-request")
                .build();

        kafkaLinkTemplate.send(message);
    }

    public void sendCardDTO_ToBank(CardValidationRequest cardValidationRequest) {
        Message<CardValidationRequest> message = MessageBuilder
                .withPayload(cardValidationRequest)
                .setHeader(KafkaHeaders.TOPIC, "bank-card-validation")
                .build();

        kafkaCardTemplate.send(message);
    }

    public void sendCardToPayment(CardResponse cardResponse) {
        Message<CardResponse> message = MessageBuilder
                .withPayload(cardResponse)
                .setHeader(KafkaHeaders.TOPIC, "merchant-card-response")
                .build();

        kafkaCardResponseTemplate.send(message);
    }

}
