package com.andrew.merchant_service.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic bankCardValidation(){
        return TopicBuilder
                .name("bank-card-validation")
                .build();
    }

    @Bean
    public NewTopic linkRequest(){
        return TopicBuilder
                .name("link-request")
                .build();
    }

    @Bean
    public NewTopic cardResponse(){
        return TopicBuilder
                .name("merchant-card-response")
                .build();
    }


}
