package com.andrew.payment_service.repository;

import com.andrew.payment_service.entity.PaymentLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentLinkRepository extends JpaRepository<PaymentLink, String> {
    Optional<PaymentLink> findByLinkId(String linkId);
}
