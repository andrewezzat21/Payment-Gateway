package com.andrew.bank_service.kafka;

import com.andrew.bank_service.dto.CardInfoDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "bank-card-validation", groupId = "paymentGateway")
    public void consumeMessage(@Payload CardInfoDTO cardInfoDto){
        System.out.println(cardInfoDto.toString());
    }

}
