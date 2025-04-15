package com.andrew.payment_service.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic linkResponse() {
        return TopicBuilder
                .name("link-response")
                .build();
    }

    @Bean
    public NewTopic cardRequest() {
        return TopicBuilder
                .name("card-request")
                .build();
    }

    @Bean
    public NewTopic transactionRequest() {
        return TopicBuilder
                .name("transaction-request")
                .build();
    }

}