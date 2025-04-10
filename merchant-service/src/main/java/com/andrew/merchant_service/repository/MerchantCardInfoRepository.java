package com.andrew.merchant_service.repository;

import com.andrew.merchant_service.entity.MerchantCardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantCardInfoRepository extends JpaRepository<MerchantCardInfo, Long> {
}
