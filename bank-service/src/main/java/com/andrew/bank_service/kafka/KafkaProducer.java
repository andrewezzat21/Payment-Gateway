package com.andrew.bank_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, CardValidationResponse> kafkaTemplate;

    public void sendCardResponse(CardValidationResponse cardValidationResponse) {
        Message<CardValidationResponse> message = MessageBuilder
                .withPayload(cardValidationResponse)
                .setHeader(KafkaHeaders.TOPIC, "card-response")
                .build();

        kafkaTemplate.send(message);
    }
}