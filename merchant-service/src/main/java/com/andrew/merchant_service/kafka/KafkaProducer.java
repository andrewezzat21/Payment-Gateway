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

    private final KafkaTemplate<String, CardValidationRequest> kafkaTemplate;

    public void sendCardDTO_ToBank(CardValidationRequest cardValidationRequest) {
        Message<CardValidationRequest> message = MessageBuilder
                .withPayload(cardValidationRequest)
                .setHeader(KafkaHeaders.TOPIC, "bank-card-validation")
                .build();

        kafkaTemplate.send(message);
    }
}
