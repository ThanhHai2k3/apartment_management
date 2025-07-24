package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.dto.request.LoginRequest;
import com.example.apartmentmanagement.dto.request.RegisterRequest;
import com.example.apartmentmanagement.dto.response.JwtResponse;

public interface AuthService {
    void register(RegisterRequest request);
    JwtResponse login(LoginRequest request);
}
