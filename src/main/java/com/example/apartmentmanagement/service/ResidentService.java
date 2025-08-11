package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.dto.request.ResidentRequest;
import com.example.apartmentmanagement.dto.response.ResidentResponse;

import java.util.List;

public interface ResidentService {
    ResidentResponse createResident (ResidentRequest request);
    ResidentResponse updateResident (Long id, ResidentRequest request);
    void deleteResident (Long id);
    ResidentResponse getResidentById (Long id);
    List<ResidentResponse> getAllResidents();
}
