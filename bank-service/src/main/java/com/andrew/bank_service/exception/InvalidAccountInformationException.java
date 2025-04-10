package com.andrew.bank_service.exception;

public class InvalidAccountInformationException extends RuntimeException{
    public InvalidAccountInformationException(String message) {
        super(message);
    }
}
