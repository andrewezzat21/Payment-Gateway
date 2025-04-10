package com.andrew.merchant_service.exception;

import com.andrew.merchant_service.dto.ApiResponse;
import com.andrew.merchant_service.dto.MerchantResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<MerchantResponse>> handleValidationExceptions(MethodArgumentNotValidException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(exception.getBindingResult().getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<MerchantResponse>> handleAllExceptions(Exception exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),null));
    }

}
