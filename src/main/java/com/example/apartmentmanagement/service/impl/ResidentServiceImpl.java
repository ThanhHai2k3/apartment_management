package com.example.apartmentmanagement.service.impl;

import com.example.apartmentmanagement.dto.request.ResidentRequest;
import com.example.apartmentmanagement.dto.response.ResidentResponse;
import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.Resident;
import com.example.apartmentmanagement.enums.ErrorCode;
import com.example.apartmentmanagement.exception.AppException;
import com.example.apartmentmanagement.mapper.ResidentMapper;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import com.example.apartmentmanagement.repository.ResidentRepository;
import com.example.apartmentmanagement.service.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResidentServiceImpl implements ResidentService {
    private final ResidentRepository residentRepository;
    private final ApartmentRepository apartmentRepository;
    private final ResidentMapper residentMapper;

    @Override
    public ResidentResponse createResident (ResidentRequest request){
        // Chỉ cần validate xem apartment toonf tại hay không vì set apartment có trong MapStruct rồi
        apartmentRepository.findById(request.getApartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));

        Resident resident = residentMapper.toResident(request);
        Resident savedResident = residentRepository.save(resident);

        return residentMapper.toResidentResponse(savedResident);
    }

    @Override
    public ResidentResponse updateResident(Long id, ResidentRequest request){
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESIDENT_NOT_FOUND));

        apartmentRepository.findById(request.getApartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));

        residentMapper.updateResident(resident, request);
        Resident updatedResident = residentRepository.save(resident);

        return residentMapper.toResidentResponse(updatedResident);
    }

    @Override
    public void deleteResident(Long id){
        residentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESIDENT_NOT_FOUND));

        residentRepository.deleteById(id);
    }

    @Override
    public ResidentResponse getResidentById (Long id){
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESIDENT_NOT_FOUND));

        return residentMapper.toResidentResponse(resident);
    }

    @Override
    public List<ResidentResponse> getAllResidents(){
        return residentRepository.findAll()
                .stream()
                .map(residentMapper::toResidentResponse)
                .collect(Collectors.toList());
    }
}
