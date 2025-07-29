package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.dto.request.ApartmentRequest;
import com.example.apartmentmanagement.dto.response.ApartmentResponse;

import java.util.List;

public interface ApartmentService {
    ApartmentResponse createApartment(ApartmentRequest request);
    ApartmentResponse updateApartment(Long id, ApartmentRequest request);
    void deleteApartment(Long id);
    ApartmentResponse getApartmentById(Long id);
    List<ApartmentResponse> getAllApartments();
}
