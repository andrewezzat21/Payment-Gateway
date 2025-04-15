package com.andrew.merchant_service.kafka;

import com.andrew.merchant_service.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final MerchantService merchantService;

    @KafkaListener(topics = "card-response", groupId = "paymentGateway")
    public void consumeCardResponse(@Payload CardValidationResponse cardValidationResponse){
        merchantService.updateCardInfo(cardValidationResponse);
    }

    @KafkaListener(topics = "link-response", groupId = "paymentGateway")
    public void consumeLinkResponse(@Payload KafkaLinkResponse kafkaLinkResponse){
        // TODO: Notify merchant: kafkaLinkResponse.merchantID
        //       about creation of the link: kafkaLinkResponse.linkId
    }

}