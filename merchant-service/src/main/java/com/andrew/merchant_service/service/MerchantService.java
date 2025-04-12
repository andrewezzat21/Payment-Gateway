package com.andrew.merchant_service.service;

import com.andrew.merchant_service.dto.*;
import com.andrew.merchant_service.entity.Merchant;
import com.andrew.merchant_service.entity.MerchantCardInfo;
import com.andrew.merchant_service.entity.MerchantDetails;
import com.andrew.merchant_service.kafka.CardValidationRequest;
import com.andrew.merchant_service.kafka.CardValidationResponse;
import com.andrew.merchant_service.kafka.KafkaProducer;
import com.andrew.merchant_service.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;
    private final EncryptionService encryptionService;
    private final KafkaProducer kafkaProducer;
    private static final Map<String, Long> requestIDMerchantMap = new HashMap<>();

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
                .password(encryptionService.encodePassword(merchantDTO.password()))
                .apiKey(createApiKey())
                .build();

        MerchantDetails merchantDetails = MerchantDetails.builder()
                .firstName(merchantDTO.firstName())
                .lastName(merchantDTO.lastName())
                .phoneNumber(merchantDTO.phoneNumber())
                .merchant(merchant)
                .build();

        merchant.setMerchantDetails(merchantDetails);

        MerchantCardInfo merchantCardInfo = new MerchantCardInfo();
        merchantCardInfo.setMerchant(merchant);
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

        if (!encryptionService.comparePassword(loginDTO.password(), merchant.getPassword())) {
            throw new RuntimeException("Email and/or Password is incorrect!");
        }

        return new ApiKeyResponse(merchant.getApiKey());
    }

    public Merchant getProfile(String apiKey) {
        return merchantRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("Profile not Found!"));
    }

    @Transactional
    public MerchantResponse updateProfile(String apiKey, MerchantDTO merchantDTO) {
        Merchant merchant = merchantRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("Profile not Found!"));
        MerchantDetails merchantDetails = merchant.getMerchantDetails();

        merchant.setEmail(merchantDTO.email());
        merchant.setPassword(encryptionService.encodePassword(merchantDTO.password()));

        merchantDetails.setFirstName(merchantDTO.firstName());
        merchantDetails.setLastName(merchantDTO.lastName());
        merchantDetails.setPhoneNumber(merchantDTO.phoneNumber());


        merchant.setMerchantDetails(merchantDetails);
        merchantRepository.save(merchant);

        return MerchantResponse.builder()
                .merchantId(merchant.getMerchantId())
                .firstName(merchantDetails.getFirstName())
                .lastName(merchantDetails.getLastName())
                .email(merchant.getEmail())
                .build();
    }


    public void validateCardInfo(String apiKey, CardInfoDTO cardInfoDTO){
        Merchant merchant = merchantRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("Profile not Found!"));

        CardInfoDTO encryptedCardInfo = new CardInfoDTO(
                encryptionService.encrypt(cardInfoDTO.cardNumber()),
                cardInfoDTO.cardType(),
                encryptionService.encrypt(cardInfoDTO.cardholderName()),
                cardInfoDTO.expiryDate()
        );

        String requestId = UUID.randomUUID().toString();
        Long merchantId = merchant.getMerchantId();
        requestIDMerchantMap.put(requestId,merchantId);

        CardValidationRequest cardValidationRequest = new CardValidationRequest(requestId, encryptedCardInfo);
        kafkaProducer.sendCardDTO_ToBank(cardValidationRequest);
    }

    @Transactional
    public void updateCardInfo(CardValidationResponse cardValidationResponse) {

        String requestId = cardValidationResponse.requestId();
        CardInfoDTO cardInfoDTO = cardValidationResponse.cardInfoDTO();
        Long merchantId = requestIDMerchantMap.get(requestId);
        requestIDMerchantMap.remove(requestId);

        String status = cardValidationResponse.status();

        if(status.equals("true")){
            Merchant merchant = merchantRepository.findByMerchantId(merchantId)
                    .orElseThrow(() -> new RuntimeException("Profile not Found!"));

            MerchantCardInfo merchantCardInfo = merchant.getMerchantCardInfo();

            merchantCardInfo.setCardType(cardInfoDTO.cardType());
            merchantCardInfo.setExpiryDate(cardInfoDTO.expiryDate());
            merchantCardInfo.setCardNumber(encryptionService.encrypt(cardInfoDTO.cardNumber()));
            merchantCardInfo.setCardholderName(encryptionService.encrypt(cardInfoDTO.cardholderName()));

            merchantRepository.save(merchant);

            //TODO : Notification of confirmation of a valid card register

        }
        else{
            //TODO : Notification of invalid card card register
        }
    }
}
