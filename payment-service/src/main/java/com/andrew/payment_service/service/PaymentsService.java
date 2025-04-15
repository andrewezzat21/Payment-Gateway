package com.andrew.payment_service.service;

import com.andrew.payment_service.entity.PaymentLink;
import com.andrew.payment_service.entity.Status;
import com.andrew.payment_service.kafka.KafkaLinkRequest;
import com.andrew.payment_service.kafka.KafkaLinkResponse;
import com.andrew.payment_service.kafka.KafkaProducer;
import com.andrew.payment_service.repository.PaymentLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentsService {

    private final PaymentLinkRepository paymentLinkRepository;
    private final KafkaProducer kafkaProducer;


    @Transactional
    public void createPaymentLink(KafkaLinkRequest kafkaLinkRequest) {
        PaymentLink paymentLink = PaymentLink.builder()
                .linkId(kafkaLinkRequest.linkId())
                .amount(kafkaLinkRequest.amount())
                .status(Status.PENDING)
                .merchantId(kafkaLinkRequest.merchantId())
                .currency(kafkaLinkRequest.currency())
                .build();

        paymentLinkRepository.save(paymentLink);
        kafkaProducer.linkResponse(new KafkaLinkResponse(kafkaLinkRequest.merchantId(), paymentLink.getLinkId()));
    }
}
