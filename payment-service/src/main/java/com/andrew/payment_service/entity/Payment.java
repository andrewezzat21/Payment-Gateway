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
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Email(message = "Email is not valid")
    private String customerEmail;

    private Double amount;

    private Status status;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "payment_link_id", nullable = false)
    private PaymentLink paymentLink;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
