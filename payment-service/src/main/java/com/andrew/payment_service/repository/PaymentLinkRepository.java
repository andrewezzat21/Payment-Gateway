package com.andrew.payment_service.repository;

import com.andrew.payment_service.entity.PaymentLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentLinkRepository extends JpaRepository<PaymentLink, String> {
}
