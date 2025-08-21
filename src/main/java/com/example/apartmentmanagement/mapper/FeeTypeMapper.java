package com.example.apartmentmanagement.mapper;

import com.example.apartmentmanagement.dto.request.FeeTypeRequest;
import com.example.apartmentmanagement.dto.response.FeeTypeResponse;
import com.example.apartmentmanagement.entity.FeeType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeeTypeMapper {
    FeeType toEntity(FeeTypeRequest request);
    FeeTypeResponse toResponse(FeeType entity);
}
