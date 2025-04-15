package com.andrew.payment_service.entity;

import com.andrew.payment_service.dto.Currency;
import com.andrew.payment_service.dto.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment_links")
@Builder
public class PaymentLink {


    @Id
    private String linkId;

    private Long merchantId;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime expirationDate;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (this.expirationDate == null) {
            this.expirationDate = LocalDateTime.now().plusHours(12);
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

}
