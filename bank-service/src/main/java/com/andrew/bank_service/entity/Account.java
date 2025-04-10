package com.andrew.bank_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @NotBlank(message = "Card number is required")
    @Size(min = 16, max = 16, message = "Card number must be 16 digits")
    private String cardNumber;

    @NotNull(message = "Card type is required")
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @NotBlank(message = "CVV is required")
    @Pattern(regexp = "\\d{3,4}", message = "CVV must be 3 or 4 digits")
    private String cvv;

    @NotBlank(message = "Cardholder name is required")
    @Size(max = 100, message = "Cardholder name must be at most 100 characters")
    private String cardholderName;

    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance must be non-negative")
    private Double balance;

}
