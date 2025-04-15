package com.andrew.payment_service.exception;

public class PaymentLinkExpiredException extends RuntimeException{
    public PaymentLinkExpiredException(String message) {
        super(message);
    }
}
