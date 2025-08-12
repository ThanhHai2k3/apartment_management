package com.example.apartmentmanagement.mapper;

import com.example.apartmentmanagement.dto.request.ResidentRequest;
import com.example.apartmentmanagement.dto.response.ResidentResponse;
import com.example.apartmentmanagement.entity.Resident;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ResidentMapper {

    @Mapping(target = "apartment.id", source = "apartmentId")
    Resident toResident(ResidentRequest request);

    @Mapping(target = "apartmentId", source = "apartment.id")
    @Mapping(target = "apartmentNumber", source = "apartment.number")
    ResidentResponse toResidentResponse(Resident resident);

    @Mapping(target = "apartment.id", source = "apartmentId")
    void updateResident(@MappingTarget Resident resident, ResidentRequest request);
}
