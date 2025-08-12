package com.example.apartmentmanagement.service.impl;

import com.example.apartmentmanagement.dto.request.ApartmentRequest;
import com.example.apartmentmanagement.dto.response.ApartmentResponse;
import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.enums.ErrorCode;
import com.example.apartmentmanagement.exception.AppException;
import com.example.apartmentmanagement.mapper.ApartmentMapper;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import com.example.apartmentmanagement.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentMapper apartmentMapper;

    @Override
    public ApartmentResponse createApartment(ApartmentRequest request){
        Apartment apartment = apartmentMapper.toApartment(request);
        Apartment savedApartment = apartmentRepository.save(apartment);
        return apartmentMapper.toApartmentResponse(savedApartment);
    }

    @Override
    public ApartmentResponse updateApartment(Long id, ApartmentRequest request){
        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));

        apartmentMapper.updateApartment(apartment, request);
        Apartment updatedApartment = apartmentRepository.save(apartment);

        return apartmentMapper.toApartmentResponse(updatedApartment);
    }

    @Override
    public ApartmentResponse getApartmentById(Long id){
        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));
        return apartmentMapper.toApartmentResponse(apartment);
    }

    @Override
    public List<ApartmentResponse> getAllApartments(){
        List<ApartmentResponse> list = apartmentRepository.findAll()
                .stream().map(apartmentMapper::toApartmentResponse)
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public void deleteApartment(Long id){
        apartmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));

        apartmentRepository.deleteById(id);
    }
}
