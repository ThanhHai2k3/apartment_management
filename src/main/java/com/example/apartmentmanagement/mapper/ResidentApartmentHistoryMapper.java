package com.example.apartmentmanagement.mapper;

import com.example.apartmentmanagement.dto.response.ResidentApartmentHistoryResponse;
import com.example.apartmentmanagement.entity.ResidentApartmentHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResidentApartmentHistoryMapper {

    @Mapping(target = "residentId", source = "resident.id")
    @Mapping(target = "apartmentId", source = "apartment.id")
    @Mapping(target = "apartmentNumber", source = "apartment.number")
    ResidentApartmentHistoryResponse toResponse(ResidentApartmentHistory history);

    List<ResidentApartmentHistoryResponse> toResponses(List<ResidentApartmentHistory> histories);
}
