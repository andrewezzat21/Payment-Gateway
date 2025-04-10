package com.andrew.merchant_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MerchantResponse {

    private Long merchantId;
    private String email;
    private String firstName;
    private String lastName;

}
