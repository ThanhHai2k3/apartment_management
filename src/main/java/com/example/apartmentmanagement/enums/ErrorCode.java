package com.example.apartmentmanagement.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND),
    INVALID_REQUEST(1002, "Invalid request data", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(1003, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCESS_DENIED(1004, "Access denied", HttpStatus.FORBIDDEN),
    DATA_NOT_FOUND(1005, "Data not found", HttpStatus.NOT_FOUND),
    USERNAME_INVALID (1006, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID (1007, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST)
    ;

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}
