package com.andrew.merchant_service.service;

import com.andrew.merchant_service.dto.ApiKeyResponse;
import com.andrew.merchant_service.dto.LoginDTO;
import com.andrew.merchant_service.dto.MerchantDTO;
import com.andrew.merchant_service.dto.MerchantResponse;
import com.andrew.merchant_service.entity.Merchant;
import com.andrew.merchant_service.entity.MerchantCardInfo;
import com.andrew.merchant_service.entity.MerchantDetails;
import com.andrew.merchant_service.repository.MerchantCardInfoRepository;
import com.andrew.merchant_service.repository.MerchantDetailsRepository;
import com.andrew.merchant_service.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;
    private final MerchantCardInfoRepository merchantCardInfoRepository;
    private final MerchantDetailsRepository merchantDetailsRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public String createApiKey() {
        return UUID.randomUUID().toString();
    }

    @Transactional
    public MerchantResponse registerNewMerchant(MerchantDTO merchantDTO) {

        if(merchantRepository.existsByEmail(merchantDTO.email())){
            throw new RuntimeException("Already have an account with email: " + merchantDTO.email());
        }

        Merchant merchant = Merchant
                .builder()
                .email(merchantDTO.email())
                .password(passwordEncoder.encode(merchantDTO.password()))
                .apiKey(createApiKey())
                .build();

        MerchantDetails merchantDetails = MerchantDetails.builder()
                .firstName(merchantDTO.firstName())
                .lastName(merchantDTO.lastName())
                .phoneNumber(merchantDTO.phoneNumber())
                .merchant(merchant)
                .build();

        MerchantCardInfo merchantCardInfo = MerchantCardInfo.builder()
                .cvv(merchantDTO.cvv())
                .merchant(merchant)
                .cardholderName(merchantDTO.cardholderName())
                .cardNumber(merchantDTO.cardNumber())
                .cardType(merchantDTO.cardType())
                .expiryDate(merchantDTO.expiryDate())
                .build();

        merchant.setMerchantDetails(merchantDetails);
        merchant.setMerchantCardInfo(merchantCardInfo);
        merchantRepository.save(merchant);

        return MerchantResponse.builder()
                .merchantId(merchant.getMerchantId())
                .firstName(merchantDetails.getFirstName())
                .lastName(merchantDetails.getLastName())
                .email(merchant.getEmail())
                .build();
    }

    public ApiKeyResponse merchantLogin(LoginDTO loginDTO) {
        Merchant merchant = merchantRepository.findByEmail(loginDTO.email()).
                orElseThrow(() -> new RuntimeException("Email and/or Password is incorrect!"));

        if (!passwordEncoder.matches(loginDTO.password(), merchant.getPassword())) {
            throw new RuntimeException("Email and/or Password is incorrect!");
        }

        return new ApiKeyResponse(merchant.getApiKey());
    }

    public Merchant getProfile(String apiKey) {
        return merchantRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("Profile not Found!"));
    }

    @Transactional
    public Merchant updateProfile(String apiKey, MerchantDTO merchantDTO) {
        Merchant merchant = merchantRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("Profile not Found!"));
        MerchantDetails merchantDetails = merchant.getMerchantDetails();
        MerchantCardInfo merchantCardInfo = merchant.getMerchantCardInfo();

        merchant.setEmail(merchantDTO.email());
        merchant.setPassword(passwordEncoder.encode(merchantDTO.password()));

        merchantDetails.setFirstName(merchantDTO.firstName());
        merchantDetails.setLastName(merchantDTO.lastName());
        merchantDetails.setPhoneNumber(merchantDTO.phoneNumber());

        merchantCardInfo.setCardNumber(merchantDTO.cardNumber());
        merchantCardInfo.setCardType(merchantDTO.cardType());
        merchantCardInfo.setCvv(merchantDTO.cvv());
        merchantCardInfo.setExpiryDate(merchantDTO.expiryDate());
        merchantCardInfo.setCardholderName(merchantDTO.cardholderName());

        merchant.setMerchantDetails(merchantDetails);
        merchant.setMerchantCardInfo(merchantCardInfo);
        merchantRepository.save(merchant);

        return merchant;
    }
}
