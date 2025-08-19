package com.example.apartmentmanagement.mapper;

import com.example.apartmentmanagement.dto.request.ResidentApartmentHistoryRequest;
import com.example.apartmentmanagement.dto.request.ResidentRequest;
import com.example.apartmentmanagement.dto.response.ResidentApartmentHistoryResponse;
import com.example.apartmentmanagement.dto.response.ResidentResponse;
import com.example.apartmentmanagement.entity.Resident;
import com.example.apartmentmanagement.entity.ResidentApartmentHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ResidentMapper {

    Resident toResident(ResidentRequest request);

    ResidentResponse toResidentResponse(Resident resident);

    void updateResident(@MappingTarget Resident resident, ResidentRequest request);

    @Mapping(target = "residentId", source = "resident.id")
    @Mapping(target = "apartmentId", source = "apartment.id")
    @Mapping(target = "apartmentNumber", source = "apartment.number")
    ResidentApartmentHistoryResponse toHistoryResponse(ResidentApartmentHistory history);

    ResidentApartmentHistory toHistory(ResidentApartmentHistoryRequest request);

    List<ResidentApartmentHistoryResponse> toHistoryResponseList(List<ResidentApartmentHistory> histories);

    // Mapping Resident -> ResidentResponse (bao gồm histories)
    @Mapping(source = "histories", target = "histories")
    ResidentResponse toResponse(Resident resident);

    // Mapping request -> entity Resident
    Resident toEntity(ResidentRequest request);
}
