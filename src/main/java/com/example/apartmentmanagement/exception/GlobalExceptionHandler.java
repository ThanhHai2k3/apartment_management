package com.example.apartmentmanagement.exception;

import com.example.apartmentmanagement.dto.response.ErrorResponse;
import com.example.apartmentmanagement.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ErrorResponse> handlingUnwantedException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(errorCode.getCode());
        errorResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ErrorResponse> handlingException(Exception exception){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        errorResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
