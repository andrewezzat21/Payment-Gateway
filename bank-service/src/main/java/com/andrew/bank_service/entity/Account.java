package com.andrew.bank_service.entity;

import com.andrew.bank_service.dto.CardType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @NotNull(message = "Card type is required")
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @NotBlank(message = "Cardholder name is required")
    private String cardholderName;

    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance must be non-negative")
    private Double balance;

    @NotNull(message = "Expiry date is required")
    private LocalDate expiryDate;

}
