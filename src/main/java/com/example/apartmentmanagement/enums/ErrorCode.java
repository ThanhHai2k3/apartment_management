package com.example.apartmentmanagement.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(1002, "Username already existed", HttpStatus.CONFLICT),
    ROLE_NOT_FOUND(1003, "Role not found", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR(1004, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCESS_DENIED(1005, "User does not have permission to access this resource", HttpStatus.FORBIDDEN),
    INVALID_CREDENTIAL(1006, "Invalid username or password", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID (1007, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID (1008, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    APARTMENT_NOT_FOUND(1009, "Apartment not found", HttpStatus.NOT_FOUND),
    RESIDENT_NOT_FOUND(1019, "Resident not found", HttpStatus.NOT_FOUND),
    ID_NUMBER_EXISTED(1011, "Identity number already existed", HttpStatus.CONFLICT),
    RESIDENT_NOT_IN_APARTMENT(1012, "Resident is not currently assigned to any apartment", HttpStatus.BAD_REQUEST),
    HISTORY_NOT_FOUND(1013, "Active residency history not found", HttpStatus.NOT_FOUND),
    INVALID_REQUEST(1014, "Invalid request data", HttpStatus.BAD_REQUEST),
    INVALID_LEVEL_REQUEST(1015, "Level must be provided for Employee", HttpStatus.BAD_REQUEST),
    LEVEL_NOT_FOUND(1016, "Level not found", HttpStatus.NOT_FOUND),
    EMPLOYEE_NOT_FOUND(1017, "Employee not found", HttpStatus.NOT_FOUND),
    EMPLOYEE_ACCESS_DENIED(1018, "Employees cannot update their level or user ID", HttpStatus.FORBIDDEN),
    USER_ALREADY_LINKED(1019, "User ID is already linked to another employee or resident", HttpStatus.BAD_REQUEST),
    USER_ROLE_ADMIN_NOT_ALLOWED(1020    , "Cannot assign user with role admin", HttpStatus.BAD_REQUEST),
    FEE_TYPE_NOT_FOUND(1021, "FeeType not found", HttpStatus.NOT_FOUND),
    FEE_TYPE_ALREADY_EXISTS(1022, "FeeType already existed", HttpStatus.CONFLICT),
    BILL_NOT_FOUND(1023, "Bill not found", HttpStatus.NOT_FOUND),
    ;

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}
