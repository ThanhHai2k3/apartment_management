package com.example.apartmentmanagement.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    USERNAME_INVALID (1, "Username must be at least 3 characters"),
    PASSWORD_INVALID (2, "Password must be at least 8 characters")
    ;

    private int code;
    private String message;

    private ErrorCode(int code, String message){
        this.code = code;
        this.message = message;
    }

}
