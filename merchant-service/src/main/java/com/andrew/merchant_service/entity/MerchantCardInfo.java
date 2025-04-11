package com.andrew.merchant_service.entity;

import com.andrew.merchant_service.dto.CardType;
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

    private String cardNumber;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private LocalDate expiryDate;

    private String cardholderName;

    @OneToOne
    @MapsId
    @JoinColumn(name = "merchant_id")
    @JsonIgnore
    private Merchant merchant;

}
