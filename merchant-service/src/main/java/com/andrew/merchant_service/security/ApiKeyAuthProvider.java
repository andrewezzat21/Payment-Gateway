package com.andrew.merchant_service.security;

import com.andrew.merchant_service.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ApiKeyAuthProvider implements AuthenticationProvider {


    private final MerchantRepository merchantRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String key = (String) authentication.getPrincipal();
        if(merchantRepository.existsByApiKey(key)) {
            return new UsernamePasswordAuthenticationToken(key, null, null);
        }

        throw new BadCredentialsException("Invalid key = " + key);
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}