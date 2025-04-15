package com.andrew.bank_service.kafka;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic cardResponse(){
        return TopicBuilder
                .name("card-response")
                .build();
    }

    @Bean
    public NewTopic transactionResponse(){
        return TopicBuilder
                .name("transaction-response")
                .build();
    }



}
