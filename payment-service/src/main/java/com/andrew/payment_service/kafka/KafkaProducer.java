package com.andrew.payment_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, KafkaLinkResponse> kafkaLinkTemplate;


    public void linkResponse(KafkaLinkResponse kafkaLinkResponse) {
        Message<KafkaLinkResponse> message = MessageBuilder
                .withPayload(kafkaLinkResponse)
                .setHeader(KafkaHeaders.TOPIC, "link-response")
                .build();

        kafkaLinkTemplate.send(message);
    }

}
