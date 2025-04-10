package com.andrew.merchant_service.repository;

import com.andrew.merchant_service.entity.MerchantDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantDetailsRepository extends JpaRepository<MerchantDetails, Long> {
}
