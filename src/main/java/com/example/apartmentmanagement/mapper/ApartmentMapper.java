package com.example.apartmentmanagement.mapper;

import com.example.apartmentmanagement.dto.request.ApartmentRequest;
import com.example.apartmentmanagement.dto.response.ApartmentResponse;
import com.example.apartmentmanagement.entity.Apartment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApartmentMapper {

    Apartment toApartment(ApartmentRequest request);
    ApartmentRequest toApartmentRequest(Apartment apartment);
    ApartmentResponse toApartmentResponse(Apartment apartment);
    List<ApartmentResponse> toApartmentResponses(List<Apartment> apartments);
    void updateApartment(@MappingTarget Apartment apartment, ApartmentRequest request);
}
