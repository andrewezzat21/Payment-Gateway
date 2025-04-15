package com.andrew.payment_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment_links")
public class PaymentLink {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long linkId;

    private Long merchantId;

    private Double amount;

    private Currency currency;

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
