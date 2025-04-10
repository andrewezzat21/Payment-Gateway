package com.andrew.merchant_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "merchants_card_info")
public class MerchantCardInfo {


    @Id
    private Long merchantId;

    @NotBlank(message = "Card number is required")
    @Size(min = 16, max = 16, message = "Card number must be 16 digits")
    private String cardNumber;

    @NotNull(message = "Card type is required")
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @NotBlank(message = "CVV is required")
    @Pattern(regexp = "\\d{3,4}", message = "CVV must be 3 or 4 digits")
    private String cvv;

    @NotNull(message = "Expiry date is required")
    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;

    @NotBlank(message = "Cardholder name is required")
    @Size(max = 100, message = "Cardholder name must be at most 100 characters")
    private String cardholderName;

    @OneToOne
    @MapsId
    @JoinColumn(name = "merchant_id")
    @JsonIgnore
    private Merchant merchant;

}
