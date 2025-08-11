package com.example.apartmentmanagement.service.impl;

import com.example.apartmentmanagement.dto.request.ResidentRequest;
import com.example.apartmentmanagement.dto.response.ResidentResponse;
import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.Resident;
import com.example.apartmentmanagement.enums.ErrorCode;
import com.example.apartmentmanagement.exception.AppException;
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

    @Override
    public ResidentResponse createResident (ResidentRequest request){
        Apartment apartment = apartmentRepository.findById(request.getApartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        Resident resident = new Resident();
        resident.setFullName(request.getFullName());
        resident.setEmail(request.getEmail());
        resident.setPhoneNum(request.getPhoneNum());
        resident.setGender(request.getGender());
        resident.setIdNumber(request.getIdNumber());
        resident.setDob(request.getDob());
        resident.setApartment(apartment);

        Resident savedResident = residentRepository.save(resident);

        return mapToResponseDTO(savedResident);
    }

    @Override
    public ResidentResponse updateResident(Long id, ResidentRequest request){
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESIDENT_NOT_FOUND));

        Apartment apartment = apartmentRepository.findById(request.getApartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));

        resident.setFullName(request.getFullName());
        resident.setEmail(request.getEmail());
        resident.setPhoneNum(request.getPhoneNum());
        resident.setGender(request.getGender());
        resident.setIdNumber(request.getIdNumber());
        resident.setDob(request.getDob());
        resident.setApartment(apartment);

        Resident updatedResident = residentRepository.save(resident);
        return mapToResponseDTO(updatedResident);
    }

    @Override
    public void deleteResident(Long id){
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESIDENT_NOT_FOUND));

        residentRepository.deleteById(id);
    }

    @Override
    public ResidentResponse getResidentById (Long id){
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESIDENT_NOT_FOUND));

        return mapToResponseDTO(resident);
    }

    @Override
    public List<ResidentResponse> getAllResidents(){
        return residentRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private ResidentResponse mapToResponseDTO(Resident resident) {
        ResidentResponse dto = new ResidentResponse();
        dto.setId(resident.getId());
        dto.setFullName(resident.getFullName());
        dto.setEmail(resident.getEmail());
        dto.setPhoneNum(resident.getPhoneNum());
        dto.setGender(resident.getGender());
        dto.setIdNumber(resident.getIdNumber());
        dto.setDob(resident.getDob());
        dto.setMoveInDate(resident.getMoveInDate());
        dto.setMoveOutDate(resident.getMoveOutDate());
        dto.setApartmentId(resident.getApartment().getId());
        dto.setApartmentNumber(resident.getApartment().getNumber());
        return dto;
    }
}
