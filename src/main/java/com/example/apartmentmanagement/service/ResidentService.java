package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.dto.request.ResidentRequest;
import com.example.apartmentmanagement.dto.response.ResidentApartmentHistoryResponse;
import com.example.apartmentmanagement.dto.response.ResidentResponse;

import java.util.List;

public interface ResidentService {
    ResidentResponse createResident (ResidentRequest request);
    ResidentResponse updateResident (Long id, ResidentRequest request);
    void assignResidentToApartment(Long residentId, Long apartmentId);
    void removeResidentFromApartment(Long residentId);
    List<ResidentApartmentHistoryResponse> getResidentHistory(Long residentId);
    boolean areResidentsCurrentCohabiting(Long residentId1, Long residentId2);
    boolean haveResidentsEverCohabited(Long residentId1, Long residentId2);
    void deleteResident (Long id);
    ResidentResponse getResidentById (Long id);
    List<ResidentResponse> getAllResidents();
    List<ResidentResponse> getCurrentResidentsByApartment(Long apartmentId);
}
