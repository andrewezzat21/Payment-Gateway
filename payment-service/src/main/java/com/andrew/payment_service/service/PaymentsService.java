package com.andrew.payment_service.service;

import com.andrew.payment_service.dto.CardInfo;
import com.andrew.payment_service.dto.PaymentRequestDTO;
import com.andrew.payment_service.entity.Payment;
import com.andrew.payment_service.entity.PaymentLink;
import com.andrew.payment_service.dto.Status;
import com.andrew.payment_service.exception.PaymentLinkExpiredException;
import com.andrew.payment_service.kafka.*;
import com.andrew.payment_service.repository.PaymentLinkRepository;
import com.andrew.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentsService {

    private final PaymentRepository paymentRepository;
    private final PaymentLinkRepository paymentLinkRepository;
    private final KafkaProducer kafkaProducer;
    private final Map<Long, PaymentRequestDTO> paymentTransactionInfo = new HashMap<>();


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


    @Transactional
    public void createPaymentRequest(String linkId, PaymentRequestDTO paymentRequestDTO) {
        PaymentLink paymentLink = paymentLinkRepository.findByLinkId(linkId)
                .orElseThrow(() -> new RuntimeException("Payment Link is not a valid link"));

        if(paymentLink.getStatus() == Status.EXPIRED){
            throw new PaymentLinkExpiredException("Payment link is expired");
        }

        Payment payment = Payment.builder()
                .amount(paymentLink.getAmount())
                .customerEmail(paymentRequestDTO.customerEmail())
                .status(Status.PENDING)
                .paymentLink(paymentLink)
                .build();

        paymentRepository.save(payment);
        paymentTransactionInfo.put(payment.getPaymentId(), paymentRequestDTO);
        kafkaProducer.cardRequest(new CardRequest(payment.getPaymentId(), paymentLink.getMerchantId()));
    }

    public void createTransactionRequest(CardResponse cardResponse) {

        Payment payment = paymentRepository.findByPaymentId(cardResponse.paymentId())
                .orElseThrow(() -> new RuntimeException("Payment id not found"));

        PaymentRequestDTO paymentRequestDTO = paymentTransactionInfo.get(payment.getPaymentId());
        paymentTransactionInfo.remove(payment.getPaymentId());

        TransactionRequest transactionRequest = new TransactionRequest(
                payment.getPaymentId(),
                cardResponse.merchantCardInfo(),
                new CardInfo(paymentRequestDTO.cardNumber(), paymentRequestDTO.cardholderName(), paymentRequestDTO.cardType(), paymentRequestDTO.expiryDate()),
                payment.getPaymentLink().getAmount()
        );
        kafkaProducer.transactionRequest(transactionRequest);
    }

    @Transactional
    public void updatePaymentStatus(TransactionResponse transactionResponse) {
        Payment payment = paymentRepository.findByPaymentId(transactionResponse.paymentId())
                .orElseThrow(() -> new RuntimeException("Payment id not found"));

        payment.setStatus(transactionResponse.status());

        PaymentLink paymentLink = payment.getPaymentLink();
        paymentLink.setStatus(Status.EXPIRED);
        paymentLinkRepository.save(paymentLink);
        paymentRepository.save(payment);

        // TODO : notify customer and merchant about the payment update
    }
}
