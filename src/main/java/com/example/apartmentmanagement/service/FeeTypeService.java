package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.dto.request.FeeTypeRequest;
import com.example.apartmentmanagement.dto.response.FeeTypeResponse;
import com.example.apartmentmanagement.entity.FeeType;

import java.util.List;

public interface FeeTypeService {
    FeeTypeResponse createFeeType(FeeTypeRequest request);
    FeeTypeResponse updateFeeType(Long id, FeeTypeRequest request);
    void deleteFeeType(Long id);
    List<FeeTypeResponse> getAllFeeTypes();
    FeeTypeResponse getFeeTypeById(Long id);
}
