package com.example.apartmentmanagement.service.impl;

import com.example.apartmentmanagement.dto.request.ApartmentRequest;
import com.example.apartmentmanagement.dto.response.ApartmentResponse;
import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import com.example.apartmentmanagement.service.ApartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    public ApartmentResponse createApartment(ApartmentRequest request){
        Apartment apartment = mapToEntity(request);
        Apartment saved = apartmentRepository.save(apartment);
        return mapToResponse(saved);
    }

    private Apartment mapToEntity(ApartmentRequest request){
        Apartment apartment = new Apartment();
        apartment.setBuilding(request.getBuilding());
        apartment.setNumber(request.getNumber());
        apartment.setFloor(request.getFloor());
        apartment.setArea(request.getArea());
        apartment.setCapacity(request.getCapacity());
        apartment.setStatus(request.getStatus());

        return apartment;
    }

    private ApartmentResponse mapToResponse(Apartment apartment){
        ApartmentResponse apartmentResponse = new ApartmentResponse();
        apartmentResponse.setId(apartment.getId());
        apartmentResponse.setBuilding(apartment.getBuilding());
        apartmentResponse.setNumber(apartment.getNumber());
        apartmentResponse.setFloor(apartment.getFloor());
        apartmentResponse.setArea(apartment.getArea());
        apartmentResponse.setCapacity(apartment.getCapacity());
        apartmentResponse.setStatus(apartment.getStatus());
        apartmentResponse.setCreatedAt(apartment.getCreatedAt());
        apartmentResponse.setUpdatedAt(apartment.getUpdatedAt());

        return apartmentResponse;
    }

    @Override
    public ApartmentResponse updateApartment(Long id, ApartmentRequest request){
        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apartment not found with Id: " + id));

        apartment.setBuilding(request.getBuilding());
        apartment.setNumber(request.getNumber());
        apartment.setFloor(request.getFloor());
        apartment.setArea(request.getArea());
        apartment.setCapacity(request.getCapacity());
        apartment.setStatus(request.getStatus());

        Apartment updated = apartmentRepository.save(apartment);
        return mapToResponse(updated);
    }

    @Override
    public ApartmentResponse getApartmentById(Long id){
        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apartment not found with Id: " + id));
        return mapToResponse(apartment);
    }

    @Override
    public List<ApartmentResponse> getAllApartments(){
        List<ApartmentResponse> list = apartmentRepository.findAll()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public void deleteApartment(Long id){
        apartmentRepository.deleteById(id);
    }
}
