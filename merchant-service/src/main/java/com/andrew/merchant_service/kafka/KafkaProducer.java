package com.andrew.merchant_service.kafka;

import com.andrew.merchant_service.dto.CardInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, CardInfoDTO> kafkaTemplate;

    public void sendMessage(CardInfoDTO cardInfoDTO){
        Message<CardInfoDTO> message = MessageBuilder
                .withPayload(cardInfoDTO)
                .setHeader(KafkaHeaders.TOPIC, "bank-card-validation")
                .build();

        kafkaTemplate.send(message);
    }

}
