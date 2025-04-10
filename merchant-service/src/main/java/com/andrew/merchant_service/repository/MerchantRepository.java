package com.andrew.merchant_service.repository;

import com.andrew.merchant_service.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    boolean existsByApiKey(String apiKey);
    Optional<Merchant> findByApiKey(String apiKey);

    boolean existsByEmail(String email);

    Optional<Merchant> findByEmail(String email);
}
